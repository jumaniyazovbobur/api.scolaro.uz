package api.scolaro.uz.service;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.auth.AuthNickNameDTO;
import api.scolaro.uz.dto.profile.*;


import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.profile.CustomProfileRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import api.scolaro.uz.service.sms.SmsHistoryService;
import api.scolaro.uz.util.JwtUtil;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import api.scolaro.uz.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomProfileRepository customProfileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private SmsHistoryService smsService;
    @Autowired
    private PersonRoleService personRoleService;
    @Autowired
    private ResourceMessageService resourceMessageService;


    public ApiResponse<String> update(ProfileUpdateDTO dto, String id) {
        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        if (dto.getCountryId() != null) {
            entity.setCountryId(dto.getCountryId());
        }
        if (dto.getPhotoId() != null) {
            entity.setPhotoId(dto.getPhotoId());
        }

        profileRepository.save(entity);
//        if (dto.getPhoneId() != null){
//            int result = profileRepository.updateDetail(EntityDetails.getCurrentUserId(), dto.getName(), dto.getSurname(),dto.getPhoneId());
//        }else {
//            int result = profileRepository.updateDetailNoPhotoId(EntityDetails.getCurrentUserId(), dto.getName(), dto.getSurname());
//        }
//        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<ProfileResponseDTO> getId(String id) {
        ProfileEntity entity = get(id);
        return ApiResponse.ok(getResponseDto(entity));
    }

    public ApiResponse<PageImpl<ProfileResponseDTO>> filter(ProfileFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<ProfileEntity> filterResultDTO = customProfileRepository.filterPagination(dto, page, size);
        return ApiResponse.ok(new PageImpl<>(filterResultDTO
                .getContent()
                .stream()
                .map(this::getResponseDto)
                .toList(), pageable, filterResultDTO.getTotalElement()));
    }

    public ApiResponse<CurrentProfileDTO> getCurrentProfile() {
        ProfileEntity profile = get(EntityDetails.getCurrentUserId());
        CurrentProfileDTO responseDTO = new CurrentProfileDTO();
        responseDTO.setName(profile.getName());
        responseDTO.setSurname(profile.getSurname());
        responseDTO.setPhone(profile.getPhone());
        responseDTO.setLang(profile.getLang());
        responseDTO.setRoleList(personRoleService.getProfileRoleList(profile.getId()));
        if (profile.getPhotoId() != null) responseDTO.setPhoto(attachService.getResponseAttach(profile.getPhotoId()));
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<?> deleted(String id) {
        ProfileEntity entity = get(id);
        int result = profileRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<?> deleteAccount() {
        ProfileEntity entity = get(EntityDetails.getCurrentUserId());
        int result = profileRepository.deleteAccount(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 1) {
            log.info("Profile deleted");
            return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.delete"));
        }
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("fail.delete"));
    }

    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto) {
        ProfileEntity details = get(EntityDetails.getCurrentUserId());
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            log.info("Confirmed password is incorrect !");
            return ApiResponse.bad("Confirmed password is incorrect !");
        }
        if (!details.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            log.info("Old password error !");
            return ApiResponse.bad("Old password error !");
        }
        int result = profileRepository.updatePassword(details.getId(), MD5Util.getMd5(dto.getNewPassword()));
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok();
    }

    public ApiResponse<?> changeStatus(String id, GeneralStatus status) {
        ProfileEntity entity = get(id);
        int result = profileRepository.changeStatus(entity.getId(), status);
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<?> updatePhone(String newPhone) {
        if (newPhone.startsWith("+")) {
            newPhone = newPhone.substring(1);
        }
        if (!PhoneUtil.validatePhone(newPhone)) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleIsTrue(newPhone);
        if (optional.isPresent()) {
            log.info("{} Phone exist", newPhone);
            return ApiResponse.bad("Phone exist !");
        }
        // random sms code
        String smsCode = RandomUtil.getRandomSmsCode();
        profileRepository.changeNewPhone(EntityDetails.getCurrentUserId(), newPhone, smsCode);
        // send new phone sms code
        String text = "Scolaro.uz tasdiqlash kodi: \n" + smsCode;
        smsService.sendMessage(newPhone, text, SmsType.CHANGE_PHONE, smsCode);

        return ApiResponse.ok("Tasdiqlash kodi yuborildi.");
    }

    public ApiResponse<?> verification(SmsDTO dto) {
        if (dto.getPhone().startsWith("+")) {
            dto.setPhone(dto.getPhone().substring(1));
        }

        if (!PhoneUtil.validatePhone(dto.getPhone())) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isPresent()) {
            log.info("{} Phone exist", dto.getPhone());
            return ApiResponse.bad("Phone exist !");
        }
        ApiResponse<?> smsResponse = smsService.checkSmsCode(dto.getPhone(), dto.getCode());
        if (smsResponse.getIsError()) {
            log.info(smsResponse.getMessage());
            return smsResponse;
        }
        ProfileEntity currentUser = get(EntityDetails.getCurrentUserId());
        if (!currentUser.getTempPhone().equals(dto.getPhone())) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        if (!currentUser.getSmsCode().equals(dto.getCode())) {
            log.info("Sms code not valid");
            return ApiResponse.bad("Sms code not valid");
        }
        int result = profileRepository.changePhone(currentUser.getId(), currentUser.getTempPhone());
        if (result == 0) return ApiResponse.bad("Try again !");

        String jwt = JwtUtil.encode(currentUser.getId(), currentUser.getTempPhone(), personRoleService.getProfileRoleList(currentUser.getId()));
        return ApiResponse.ok(jwt);

    }

    public ProfileResponseDTO getProfileInfo(String id) {
        ProfileEntity entity = get(id);
        ProfileResponseDTO responseDTO = new ProfileResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        if (entity.getPhotoId() != null) responseDTO.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        return responseDTO;
    }

    public ApiResponse<Boolean> getProfileByNickName(AuthNickNameDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByNickName(dto.getNickName());
        if (optional.isEmpty()) {
            return ApiResponse.ok(false);
        }
        return ApiResponse.ok(true);
    }

    private ProfileResponseDTO getResponseDto(ProfileEntity entity) {
        ProfileResponseDTO responseDTO = new ProfileResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        if (entity.getPhotoId() != null) responseDTO.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        return responseDTO;
    }

    public ProfileResponseDTO getProfileForApp(String id) {
        ProfileEntity entity = get(id);
        ProfileResponseDTO responseDTO = new ProfileResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        return responseDTO;
    }

    public ProfileEntity get(String id) {
        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Profile not Found");
            return new ItemNotFoundException(resourceMessageService.getMessage("profile.not-found"));
        });
    }

    public void fillStudentBalance(String profileId, Long amount) {
        profileRepository.fillStudentBalance(profileId, amount);
    }

    public boolean checkBalance(String profileId, Long amount) {
        return profileRepository.existsByIdAndBalanceIsGreaterThan(profileId, amount);
    }

    public void reduceFromBalance(String profileId, Long amount) {
        profileRepository.reduceStudentBalance(profileId, amount);
    }

    public ApiResponse<String> updateLang(AppLanguage lang, String currentUserId) {
        profileRepository.updateLang(currentUserId, lang);
        return ApiResponse.ok();
    }

    public ApiResponse<String> changeRole(String id, ChangeProfileRoleReqDTO dto) {
        List<RoleEnum> oldRoleList = personRoleService.getProfileRoleList(id);
        List<RoleEnum> list = dto.getRoles()
                .stream()
                .filter(role -> !oldRoleList.contains(role) && List.of(RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_STUDENT).contains(role))
                .toList();
        personRoleService.create(id, list);
        return ApiResponse.ok();
    }
}