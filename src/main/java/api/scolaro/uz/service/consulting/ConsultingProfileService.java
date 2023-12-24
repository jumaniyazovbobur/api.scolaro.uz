package api.scolaro.uz.service.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.ConsultingProfileDTO;
import api.scolaro.uz.dto.consultingProfile.ConsultingProfileCreateDTO;
import api.scolaro.uz.dto.consultingProfile.ConsultingProfileUpdateDTO;
import api.scolaro.uz.dto.profile.UpdatePasswordDTO;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingProfileRepository;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.PersonRoleService;
import api.scolaro.uz.service.sms.SmsHistoryService;
import api.scolaro.uz.util.JwtUtil;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import api.scolaro.uz.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultingProfileService {
    @Autowired
    private ConsultingProfileRepository consultingProfileRepository;
    @Autowired
    private SmsHistoryService smsService;
    @Autowired
    private PersonRoleService personRoleService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ConsultingService consultingService;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<String> updatePassword(UpdatePasswordDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            log.info("Confirmed password is incorrect !");
            return ApiResponse.bad("Confirmed password is incorrect !");
        }
        ConsultingProfileEntity entity = get(EntityDetails.getCurrentUserId());
        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            log.info("Old password error !");
            return ApiResponse.bad("Old password error !");
        }
        int result = consultingProfileRepository.updatePassword(entity.getId(), MD5Util.getMd5(dto.getNewPassword()));
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<String> updatePhone(String newPhone) {
        if (newPhone.startsWith("+")) {
            newPhone = newPhone.substring(1);
        }
        if (!PhoneUtil.validatePhone(newPhone)) {
            log.info("Phone not valid {}", newPhone);
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findByPhoneAndVisibleIsTrue(newPhone);
        if (optional.isPresent()) {
            log.info("{} Phone exist", newPhone);
            return ApiResponse.bad("Phone exist !");
        }
        // random sms code
        String smsCode = RandomUtil.getRandomSmsCode();
        consultingProfileRepository.changeNewPhone(EntityDetails.getCurrentUserId(), newPhone, smsCode);
        // send new phone sms code
        String text = "Scolaro.uz tasdiqlash kodi: \n" + smsCode;
        smsService.sendMessage(newPhone, text, SmsType.CHANGE_PHONE, smsCode);
        // response
        return ApiResponse.ok("Tasdiqlash kodi yuborildi.");
    }

    public ApiResponse<String> updatePhoneVerification(SmsDTO dto) {
        if (dto.getPhone().startsWith("+")) {
            dto.setPhone(dto.getPhone().substring(1));
        }

        if (!PhoneUtil.validatePhone(dto.getPhone())) {
            log.info("Phone not valid {}", dto.getPhone());
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isPresent()) {
            log.info("{} Phone exist", dto.getPhone());
            return ApiResponse.bad("Phone exist !");
        }
        ApiResponse<String> smsResponse = smsService.checkSmsCode(dto.getPhone(), dto.getCode());
        if (smsResponse.getIsError()) {
            log.info(smsResponse.getMessage());
            return smsResponse;
        }
        ConsultingProfileEntity currentUser = get(EntityDetails.getCurrentUserId());
        if (!currentUser.getTempPhone().equals(dto.getPhone())) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        if (!currentUser.getSmsCode().equals(dto.getCode())) {
            log.info("Sms code not valid");
            return ApiResponse.bad("Sms code not valid");
        }
        int result = consultingProfileRepository.changePhone(currentUser.getId(), currentUser.getTempPhone());
        if (result == 0) return ApiResponse.bad("Try again !");

        String jwt = JwtUtil.encode(currentUser.getId(), currentUser.getPhone(), personRoleService.getProfileRoleList(currentUser.getId()));
        return ApiResponse.ok(jwt);
    }

    public ApiResponse<ConsultingProfileDTO> getConsultingProfileDetail(String id) {
        ConsultingProfileEntity entity = get(EntityDetails.getCurrentUserId());

        ConsultingProfileDTO responseDTO = new ConsultingProfileDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setRoleList(personRoleService.getProfileRoleList(entity.getId()));
        if (entity.getPhotoId() != null) responseDTO.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        responseDTO.setConsulting(consultingService.getById(entity.getConsultingId()));
        return ApiResponse.ok(responseDTO);
    }

    public ConsultingProfileEntity getConsultingManagerByConsultingId(String consultingId) {
        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findConsultingManagerByConsultingId(consultingId);
        return optional.orElseGet(null);
    }

    public ConsultingProfileEntity get(String id) {
        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} consulting not found", id);
            throw new ItemNotFoundException("Consulting not found");
        }
        return optional.get();
    }

    private ConsultingProfileDTO toDTO(ConsultingProfileEntity entity) {
        ConsultingProfileDTO dto = new ConsultingProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ApiResponse<String> create(ConsultingProfileCreateDTO dto) {
        String phone = dto.getPhone();

        if (phone.startsWith("+")) {
            phone = phone.substring(1);
        }

        if (!PhoneUtil.validatePhone(phone)) {
            log.info("Phone not valid {}", phone);
            return ApiResponse.bad("Phone not valid");
        }

        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findByPhoneAndVisibleIsTrue(phone);

        if (optional.isPresent()) {
            log.info("{} Phone exist", phone);
            return ApiResponse.bad("Phone exist !");
        }

        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
        ConsultingProfileEntity entity = toEntity(dto, consultingId);

        consultingProfileRepository.save(entity);
        return ApiResponse.ok();
    }

    private ConsultingProfileEntity toEntity(ConsultingProfileCreateDTO dto, String consultingId) {
        ConsultingProfileEntity entity = new ConsultingProfileEntity();
        entity.setConsultingId(consultingId);
        entity.setAddress(dto.getAddress());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setSurname(dto.getSurname());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setPhotoId(dto.getPhotoId());
        entity.setCountryId(dto.getCountryId());
        return entity;
    }

    public ApiResponse<String> update(String id, ConsultingProfileUpdateDTO dto) {

        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Consulting profile not found! {}", id);
            return ApiResponse.bad("Profile not found");
        }

        ConsultingProfileEntity entity = optional.get();
        String consultingId = Objects.requireNonNull(EntityDetails.getCurrentUserDetail()).getProfileConsultingId();

        if (!consultingId.equals(entity.getConsultingId())) {
            log.info("Access denied! {}", id);
            return ApiResponse.forbidden("Access denied!");
        }

        entity.setConsultingId(consultingId);
        entity.setAddress(dto.getAddress());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setSurname(dto.getSurname());
        entity.setPhotoId(dto.getPhotoId());
        entity.setCountryId(dto.getCountryId());
        consultingProfileRepository.save(entity);
        return ApiResponse.ok();
    }

    public ApiResponse<String> delete(String id) {
        consultingProfileRepository.updateVisible(id, false);
        return ApiResponse.ok();
    }

    public ApiResponse<PageImpl<ConsultingProfileDTO>> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<ConsultingProfileEntity> entityPage = consultingProfileRepository.findAllByVisibleIsTrue(pageable);

        return ApiResponse.ok(
                new PageImpl<>(
                        entityPage
                                .stream()
                                .map(this::toDTO)
                                .toList(),
                        pageable,
                        entityPage.getTotalElements()
                )
        );
    }
    // currentConsulting.setRoleList(personRoleService.getProfileRoleList(details.getId()));
//    public ApiResponse<String> deleteAccount() {
//        ConsultingEntity entity = get(EntityDetails.getCurrentUserId());
//        int result = consultingProfileRepository.deleteAccount(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
//        if (result == 1) {
//            log.info("Consulting deleted");
//            return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.delete"));
//        }
//        return new ApiResponse<>(200, false, resourceMessageService.getMessage("fail.delete"));
//    }

}
