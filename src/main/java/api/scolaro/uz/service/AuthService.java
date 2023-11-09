package api.scolaro.uz.service;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.auth.*;
import api.scolaro.uz.dto.auth.AuthRequestDTO;


import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import api.scolaro.uz.service.sms.SmsHistoryService;
import api.scolaro.uz.util.JwtUtil;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final PersonRoleService personRoleService;
    private final ResourceMessageService resourceMessageService;
    private final SmsHistoryService smsHistoryService;
    private final PasswordEncoder passwordEncoder;
    private final ConsultingRepository consultingRepository;
    private final ProfileService profileService;
    private final AttachService attachService;

    public ApiResponse<String> registration(AuthRequestDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhoneNumber());
        //validate phone number
        if (!validate) {
            log.info("Phone not valid! phone={}", dto.getPhoneNumber());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }

        Optional<ProfileEntity> profileEntity = profileRepository.findByPhone(dto.getPhoneNumber());

        if (profileEntity.isPresent()) {
            if (profileEntity.get().getStatus().equals(GeneralStatus.ACTIVE) || profileEntity.get().getStatus().equals(GeneralStatus.BLOCK)) {
                log.warn("PhoneNumber already exist {}", dto.getPhoneNumber());
                return new ApiResponse<>(resourceMessageService.getMessage("phone.already.exists"), 400, true);
            }

            if (profileEntity.get().getStatus().equals(GeneralStatus.NOT_ACTIVE)) {
                // send sms for complete registration
                smsHistoryService.sendRegistrationSms(dto.getPhoneNumber());
                return new ApiResponse<>(200, false);
            }
        }
        //user create
        ProfileEntity userEntity = new ProfileEntity();
        userEntity.setName(dto.getName());
        userEntity.setSurname(dto.getSurname());
        userEntity.setPhone(dto.getPhoneNumber());
        userEntity.setPassword(MD5Util.getMd5(dto.getPassword()));
        userEntity.setStatus(GeneralStatus.NOT_ACTIVE);
        userEntity.setAddress(dto.getAddress());
        userEntity.setCountryId(dto.getCountryId());
        userEntity.setGenderType(dto.getGender());
        userEntity.setNickName(dto.getNickName());
        userEntity.setFireBaseId(dto.getFireBaseId());

        profileRepository.save(userEntity);
        // send sms verification code
        smsHistoryService.sendRegistrationSms(dto.getPhoneNumber());
        //client role
        personRoleService.create(userEntity.getId(), RoleEnum.ROLE_STUDENT);
        return new ApiResponse<>(200, false, "Success");
    }

    public ApiResponse<String> profileRegistrationVerification(SmsDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        if (!validate) {
            log.info("Phone not Valid! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }

        Optional<ProfileEntity> userOptional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());

        if (userOptional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            throw new ItemNotFoundException(resourceMessageService.getMessage("client.not.found"));
        }

        if (!userOptional.get().getStatus().equals(GeneralStatus.NOT_ACTIVE)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("wrong.client.status"), 400, true);
        }

        ApiResponse<String> smsResponse = smsHistoryService.checkSmsCode(dto.getPhone(), dto.getCode());
        if (smsResponse.getIsError()) {
            return smsResponse;
        }
        // change client status
        profileRepository.updateStatus(dto.getPhone(), GeneralStatus.ACTIVE);
        AuthResponseDTO responseDTO = getClientAuthorizationResponse(userOptional.get());
        return new ApiResponse<>(200, false, "Success registration");

    }


    public ApiResponse<AuthResponseDTO> profileLogin(AuthRequestProfileDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        if (!validate) {
            log.info("Phone not valid! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("client.not.found"), 400, true);
        }
        ProfileEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("client.status.blocked"), 400, true);
        }

        if (!passwordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            log.warn("Password wrong! username = {}", dto.getPassword());
            return new ApiResponse<>(resourceMessageService.getMessage("username.password.wrong"), 400, true);
        }

        return new ApiResponse<>(200, false, getClientAuthorizationResponse(profile));
    }


    private AuthResponseDTO getClientAuthorizationResponse(ProfileEntity entity) {
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setId(entity.getId());
        dto.setNickName(entity.getNickName());
        dto.setCountryId(entity.getCountryId());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setAttachDTO(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setName(entity.getName());
        dto.setRoleList(personRoleService.getProfileRoleList(entity.getId()));
        String jwt = JwtUtil.encode(entity.getId(), entity.getPhone(), dto.getRoleList());
        dto.setJwt(jwt);
        return dto;
    }

    private AuthResponseDTO getClientAuthorizationResponse(ConsultingEntity entity) {
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setName(entity.getName());
        dto.setAttachDTO(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setRoleList(personRoleService.getProfileRoleList(entity.getId()));
        String jwt = JwtUtil.encode(entity.getId(), entity.getPhone(), dto.getRoleList());
        dto.setJwt(jwt);
        return dto;
    }

    public ApiResponse<AuthResponseDTO> consultingLogin(AuthRequestProfileDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        if (!validate) {
            log.info("Phone not valid! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }

        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.warn("Consulting not found! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.not.found"), 400, true);
        }

        ConsultingEntity entity = optional.get();

        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("Consulting Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.status.blocked"), 400, true);
        }

        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            log.warn("Password wrong! username = {}", dto.getPassword());
            return new ApiResponse<>(resourceMessageService.getMessage("username.password.wrong"), 400, true);
        }
        AuthResponseDTO response = getClientAuthorizationResponse(entity);

        return new ApiResponse<>(200, false, response);
    }

    public ApiResponse<String> resetPasswordRequest(AuthResetProfileDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        //validate phone number
        if (!validate) {
            log.info("Phone not valid! phone={}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("client.not.found"), 400, true);
        }
        ProfileEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("client.status.blocked"), 400, true);
        }

        smsHistoryService.sendResetSms(dto.getPhone());

        return new ApiResponse<>(200, false, "Success");

    }

    public ApiResponse<AuthResponseDTO> resetPasswordConfirm(ResetPasswordConfirmDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        //validate phone number
        if (!validate) {
            log.info("Phone not valid! phone={}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.warn("Client not found! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("client.not.found"), 400, true);
        }
        ProfileEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("Profile Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("client.status.blocked"), 400, true);
        }

        ApiResponse<String> smsResponse = smsHistoryService.checkSmsCode(dto.getPhone(), dto.getSmsCode());
        if (smsResponse.getIsError()) {
            return new ApiResponse<>(smsResponse.getMessage(), 400, true);
        }

        if (!dto.getNewPassword().equals(dto.getRepeatNewPassword())) {
            log.info("Not valid password");
            return new ApiResponse<>(resourceMessageService.getMessage("password.not.matched"), 400, true);
        }

        profileRepository.updatePassword(profile.getId(), MD5Util.getMd5(dto.getNewPassword()));
        return new ApiResponse<>(200, false, getClientAuthorizationResponse(profile));
    }

    /*
     *CONSULTING RESET PASSWORD
     */
    public ApiResponse<String> resetPasswordConsultingRequest(AuthResetProfileDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        //validate phone number
        if (!validate) {
            log.info("Phone not valid! phone={}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }
        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.info("Consulting not found! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.not.found"), 400, true);
        }
        ConsultingEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.info("Consulting Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.status.blocked"), 400, true);
        }
        smsHistoryService.sendResetSms(dto.getPhone());
        return new ApiResponse<>(200, false, "Success");
    }


    public ApiResponse<AuthResponseDTO> resetPasswordConsultingConfirm(ResetPasswordConfirmDTO dto) {
        boolean validate = PhoneUtil.validatePhone(dto.getPhone());
        //validate phone number
        if (!validate) {
            log.info("Phone not valid! phone={}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("phone.validation.not-valid"), 400, true);
        }
        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isEmpty()) {
            log.info("Consulting not found! phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.not.found"), 400, true);
        }
        ConsultingEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.info("Consulting Status Blocked! Phone = {}", dto.getPhone());
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.status.blocked"), 400, true);
        }
        ApiResponse<?> smsResponse = smsHistoryService.checkSmsCode(dto.getPhone(), dto.getSmsCode());
        if (smsResponse.getIsError()) {
            return new ApiResponse<>(smsResponse.getMessage(), 400, true);
        }

        if (!dto.getNewPassword().equals(dto.getRepeatNewPassword())) {
            log.info("Not valid password");
            return new ApiResponse<>(resourceMessageService.getMessage("password.not.matched"), 400, true);
        }

        consultingRepository.updatePassword(profile.getId(), MD5Util.getMd5(dto.getNewPassword()));
        return new ApiResponse<>(200, false, getClientAuthorizationResponse(profile));
    }

//    public ProfileResponseDTO login(AuthDTO dto) {
//        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());
//        if (optional.isEmpty()) {
//            log.info("User not found.");
//            throw new ItemNotFoundException("User not found.");
//        }
//        ProfileEntity entity = optional.get();
//        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
//            log.info("Wrong status!");
//            throw new ArithmeticException("Wrong status!");
//        }
//        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
//            log.info("User not found.");
//            throw new ItemNotFoundException("User not found.");
//        }
//        ProfileResponseDTO response = new ProfileResponseDTO();
//        response.setName(entity.getName());
//        response.setSurname(entity.getSurname());
//        response.setPhone(entity.getPhone());
//        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(entity.getId());
//        response.setRoles(roleList);
//        response.setJwt(JwtUtil.encode(entity.getId(), entity.getPhone(), roleList));
//        return null;
//    }

   /* public void resetPasswordRequest(ResetPasswordRequestDTO dto) {
        if (!PhoneUtil.isValidPhone(dto.getPhone())) {
            log.info("Not valid phone number");
            throw new ItemNotFoundException("Not valid phone number");
        }
        Optional<UserEntity> optional = profileRepository.findByPhone(dto.getPhone());
        if (optional.isEmpty()) {
            log.info("Phone number not found");
            throw new ItemNotFoundException("Phone number not found");
        }
        UserEntity profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.info("Profile not active.");
            throw new ItemNotFoundException("Profile not active.");
        }
//        smsService.sendResetPasswordSms(dto.getPhone());
    }

    public void resetPasswordConfirm(ResetPasswordConfirmDTO dto) {
//        if (!PhoneUtil.isValidPhone(dto.getPhone())) {
//            log.info("Not valid phone number");
//            throw new ItemNotFoundException("Not valid phone number");
//        }
//        Optional<UserEntity> optional = profileRepository.findByPhone(dto.getPhone());
//        if (optional.isEmpty()) {
//            log.info("Phone number not found");
//            throw new ItemNotFoundException("Phone number not found");
//        }
//        UserEntity profile = optional.get();
//        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
//            log.info("Profile not active.");
//            throw new ItemNotFoundException("Profile not active.");
//        }
//        if (!smsService.checkSmsCode(dto.getPhone(), dto.getSmsCode())) {
//            log.info("Not valid sms code.");
//            throw new AppBadRequestException("Not valid sms code.");
//        }
//        if (!dto.getNewPassword().equals(dto.getRepeatNewPassword()) || dto.getNewPassword().length()<5) {
//            log.info("Not valid password");
//            throw new AppBadRequestException("Not valid password");
//        }
//        profileRepository.updatePassword(profile.getId(), MD5Util.getMd5(dto.getNewPassword()));
    }*/
}
