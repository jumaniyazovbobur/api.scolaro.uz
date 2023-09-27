package api.scolaro.uz.service;


import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.dto.profile.*;


import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.profile.CustomProfileRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import api.scolaro.uz.service.sms.SmsHistoryService;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import api.scolaro.uz.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final CustomProfileRepository customProfileRepository;

    private final AttachService attachService;

    private final SmsHistoryService smsService;

    public ApiResponse<?> update(ProfileUpdateDTO dto) {
        ProfileDTO currentProfile = getCurrentProfileDetail();
        int result = profileRepository.updateDetail(currentProfile.getId(), dto.getName(), dto.getSurname());
        if (result == 0) return ApiResponse.bad("Try again !");
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

    public ApiResponse<?> deleted(String id) {
        ProfileEntity entity = get(id);
        int result = profileRepository.deleted(entity.getId(), getCurrentProfileDetail().getId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok();
    }


    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto) {
        ProfileDTO currentProfile = getCurrentProfileDetail();
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            log.info("Confirmed password is incorrect !");
            return ApiResponse.bad("Confirmed password is incorrect !");
        }
        if (!currentProfile.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            log.info("Old password error !");
            return ApiResponse.bad("Old password error !");
        }
        int result = profileRepository.updatePassword(currentProfile.getId(), MD5Util.getMd5(dto.getNewPassword()));
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok();
    }

    public ApiResponse<?> changeStatus(String id, GeneralStatus status) {
        ProfileEntity entity = get(id);
        int result = profileRepository.changeStatus(entity.getId(), status);
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok();
    }

    public ApiResponse<?> updatePhone(String newPhone) {
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
        profileRepository.changeNewPhone(getCurrentProfileDetail().getId(), newPhone, smsCode);
        // send new phone sms code
        String text = "Scolaro.uz tasdiqlash kodi: \n" + smsCode;
        smsService.sendMessage(newPhone, text, SmsType.CHANGE_PHONE, smsCode);

        return ApiResponse.ok("Tasdiqlash kodi yuborildi.");
    }

    public ApiResponse<?> verification(SmsDTO dto) {
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
            return ApiResponse.bad(smsResponse.getMessage());
        }
        ProfileEntity currentUser = get(getCurrentProfileDetail().getId());
        if (!currentUser.getTempPhone().substring(1).equals(dto.getPhone())) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        if (!currentUser.getSmsCode().equals(dto.getCode())) {
            log.info("Sms code not valid");
            return ApiResponse.bad("Sms code not valid");
        }
        int result = profileRepository.changePhone(currentUser.getId(), currentUser.getTempPhone());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok();

    }

    private ProfileDTO getCurrentProfileDetail() {
        CustomUserDetails details = EntityDetails.getCurrentUserDetail();
        if (details == null) {
            log.info("No permission");
            throw new AppBadRequestException("No permission !");
        }
        ProfileDTO currentProfile = new ProfileDTO();
        currentProfile.setId(details.getId());
        currentProfile.setName(details.getName());
        currentProfile.setSurname(details.getSurname());
        currentProfile.setPassword(details.getPassword());
        currentProfile.setPhone(details.getPhone());
        return currentProfile;
    }

    private ProfileEntity get(String id) {
        Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} user not found", id);
            throw new ItemNotFoundException("Profile not found");
        }
        return optional.get();
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


    // TODO update phone 2 api
    // TODO update password 1 api (oldPassword, newPassword, confirmNewPassword)
    // TODO block profile only admin
    // TODO getCurrentProfileDetail() (id,name,surname,phone)


//    public ProfileDTO getCurrentProfileDetail() {
//        UserEntity profile = get(EntityDetails.getCurrentUserId());
//        return toDto(profile);
//    }
//
//    public ProfileDTO addProfile(CreateProfileDTO dto) {
//        UserEntity entity = new UserEntity();
//        entity.setName(dto.getName());
//        entity.setSurname(dto.getSurname());
//        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
//        entity.setPhone(dto.getPhone());
//        if (hasRole(dto.getRoles(), RoleEnum.ROLE_FACULTY)) {
//            if (dto.getFacultyId() == null) {
//                log.info("Faculty required");
//                throw new ItemNotFoundException("Faculty required");
//            }
//            entity.setFacultyId(dto.getFacultyId());
//        }
//        entity.setStatus(GeneralStatus.ACTIVE);
//        entity.setCreatedDate(LocalDateTime.now());
//        profileRepository.save(entity);// save profile
//        personRoleService.create(entity.getId(), dto.getRoles()); // save profile roles
//        return toDto(entity);
//    }
//
//    public ProfileDTO getById(String id) {
//        UserEntity entity = get(id);
//        if (entity == null) {
//            log.info("Such id not" + id);
//            throw new ItemNotFoundException("Such id not" + id);
//        }
//        ProfileDTO dto = toDto(entity);
//        dto.setRoles(personRoleService.getProfileRoleList(id));
//        return dto;
//    }
//
//    public Boolean deleteById(String id) {
//        Optional<UserEntity> optional = profileRepository.findById(id);
//        if (optional.isEmpty()) {
//            log.info("Profile not found");
//            throw new ItemNotFoundException("Bunaqa profile mavjud emas");
//        }
//        UserEntity entity = optional.get();
//        entity.setVisible(false);
//        profileRepository.save(entity);
//        return true;
//    }
//
//    public ProfileDTO updateProfile(String id, CreateProfileDTO dto) {
//        UserEntity entity = get(id);
//        entity.setName(dto.getName());
//        entity.setSurname(dto.getSurname());
//        entity.setPhone(dto.getPhone());
//        if (hasRole(dto.getRoles(), RoleEnum.ROLE_FACULTY)) {
//            if (dto.getFacultyId() == null) {
//                throw new ItemNotFoundException("Faculty required");
//            }
//            entity.setFacultyId(dto.getFacultyId());
//        }
//        profileRepository.save(entity);
//        return toDto(entity);
//    }
//
//    public ApiResponse<?> updateDetail(UpdateProfileDetailDTO dto) {
//        String profileId = EntityDetails.getCurrentUserId();
//        Optional<UserEntity> optional = profileRepository.findById(profileId);
//        if (optional.isEmpty()) {
//            log.info("Profile not found.");
//            throw new ItemNotFoundException("Profile not found.");
//        }
//        profileRepository.updateDetail(profileId, dto.getName(), dto.getSurname());
//        return ApiResponse.ok();
//    }
//
//    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto) {
//        String profileId = EntityDetails.getCurrentUserId();
//        UserEntity entity = get(profileId);
//        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
//            log.info("Wrong password");
//            throw new AppBadRequestException("Wrong password");
//        }
//        profileRepository.updatePassword(profileId, MD5Util.getMd5(dto.getOldPassword()));
//        return ApiResponse.ok();
//    }
//
//    public PageImpl<ProfileDTO> filter(ProfileFilterRequestDTO dto) {
//        // bitta sql queryda profile role larni ham olib kelish kerka. ularni , bilan ajratish kerak. ROLE_ADMIN, ROLE_FACULTY ...
//        // builder.append(" ,(select string_agg(role,', ') from profile_role where profile_id = p.id) as roleList");
//        return null;
//    }
//
//    public UserEntity get(String id) {
//        Optional<UserEntity> optional = profileRepository.findById(id);
//        if (optional.isEmpty()) {
//            log.info("Profile not found: " + id);
//            throw new ItemNotFoundException("Profile not found: " + id);
//        }
//        return optional.get();
//    }
//
//    public ProfileDTO toDto(UserEntity profileEntity) {
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setId(profileEntity.getId());
//        profileDTO.setName(profileEntity.getName());
//        profileDTO.setSurname(profileEntity.getSurname());
//        profileDTO.setPhone(profileEntity.getPhone());
//        profileDTO.setCreatedDate(profileEntity.getCreatedDate());
//        return profileDTO;
//    }
//
//    private boolean hasRole(List<RoleEnum> roleList, RoleEnum requiredRole) {
//        if (roleList == null || roleList.isEmpty()) {
//            return false;
//        }
//        for (RoleEnum roleEnum : roleList) {
//            if (roleEnum.equals(requiredRole)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public ResponseEntity<?> registration(AuthRequestDTO dto) {
//
//        return null;
//
//    }
}
