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
import api.scolaro.uz.util.JwtUtil;
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

    private final PersonRoleService personRoleService;

    private final ResourceMessageService resourceMessageService;

    public ApiResponse<?> update(ProfileUpdateDTO dto) {
        int result = profileRepository.updateDetail(EntityDetails.getCurrentUserId(), dto.getName(), dto.getSurname());
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
        int result = profileRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
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
        profileRepository.changeNewPhone(getCurrentProfileDetail().getId(), newPhone, smsCode);
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
        ProfileEntity currentUser = get(getCurrentProfileDetail().getId());
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

        String jwt = JwtUtil.encode(currentUser.getId(), currentUser.getPhone(), personRoleService.getProfileRoleList(currentUser.getId()));
        return ApiResponse.ok(jwt);

    }

    private ProfileDTO getCurrentProfileDetail() {
        ProfileEntity details = get(EntityDetails.getCurrentUserId());
        ProfileDTO currentProfile = new ProfileDTO();
        currentProfile.setId(details.getId());
        currentProfile.setName(details.getName());
        currentProfile.setSurname(details.getSurname());
        currentProfile.setPhone(details.getPhone());
        currentProfile.setPassword(details.getPassword());
        return currentProfile;
    }

    public ProfileEntity get(String id) {
       /* Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} user not found", id);
            throw new ItemNotFoundException("Profile not found");
        }
        return optional.get();*/
         return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Employee not Found");
            throw new ItemNotFoundException(resourceMessageService.getMessage("profile.not-found"));
        });
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
}