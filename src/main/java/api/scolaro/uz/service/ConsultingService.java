package api.scolaro.uz.service;

import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingCreateDTO;
import api.scolaro.uz.dto.consulting.ConsultingUpdateDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.entity.ConsultingEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingRepository;
import api.scolaro.uz.service.sms.SmsHistoryService;
import api.scolaro.uz.util.JwtUtil;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import api.scolaro.uz.util.RandomUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultingService {

    private final ConsultingRepository consultingRepository;

    private final CustomConsultingRepository customRepository;

    private final PersonRoleService personRoleService;

    private final AttachService attachService;

    private final SmsHistoryService smsService;

    public ApiResponse<ConsultingDTO> create(ConsultingCreateDTO dto) {
        if (dto.getPhone().startsWith("+")) {
            dto.setPhone(dto.getPhone().substring(1));
        }
        if (!PhoneUtil.validatePhone(dto.getPhone())) {
            log.info("Exception : Phone not valid {}", dto.getPhone());
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isPresent()) {
            log.info("Exception : Phone exist {}", dto.getPhone());
            return ApiResponse.bad("Phone exist");
        }
        // random sms password
        String smsPassword = RandomUtil.getRandomString(5);
        // send sms password
        String text = "Scolaro.uz platformasiga kirish uchun sizning parolingiz: \n" + smsPassword;
        smsService.sendMessage(dto.getPhone(), text, SmsType.CHANGE_PASSWORD, smsPassword);
        //
        ConsultingEntity entity = new ConsultingEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5(smsPassword));
        entity.setAbout(dto.getAbout());
        entity.setPhotoId(dto.getPhotoId());
        entity.setStatus(GeneralStatus.ACTIVE);
        // save
        consultingRepository.save(entity);
        personRoleService.create(entity.getId(), RoleEnum.ROLE_CONSULTING);
        // response
        return ApiResponse.ok(toDTO(entity));
    }


    public ApiResponse<?> updateDetail(ConsultingUpdateDTO dto) {
        ConsultingEntity entity = get(EntityDetails.getCurrentUserId());

        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setAbout(dto.getAbout());
        // update
        consultingRepository.save(entity);
        // response
        return ApiResponse.ok(toDTO(entity));
    }

    // TODO api for update password (old and newPassword , confirmNewPassword)


    public ApiResponse<?> updatePhone(String newPhone) {
        if (newPhone.startsWith("+")) {
            newPhone = newPhone.substring(1);
        }
        if (!PhoneUtil.validatePhone(newPhone)) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(newPhone);
        if (optional.isPresent()) {
            log.info("{} Phone exist", newPhone);
            return ApiResponse.bad("Phone exist !");
        }
        // random sms code
        String smsCode = RandomUtil.getRandomSmsCode();
//        consultingRepository.changeNewPhone(getCurrentProfileDetail().getId(), newPhone, smsCode);
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
        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isPresent()) {
            log.info("{} Phone exist", dto.getPhone());
            return ApiResponse.bad("Phone exist !");
        }
        ApiResponse<?> smsResponse = smsService.checkSmsCode(dto.getPhone(), dto.getCode());
        if (smsResponse.getIsError()) {
            log.info(smsResponse.getMessage());
            return smsResponse;
        }
        ConsultingEntity currentUser = get("");
        if (!currentUser.getTempPhone().equals(dto.getPhone())) {
            log.info("Phone not valid");
            return ApiResponse.bad("Phone not valid");
        }
        if (!currentUser.getSmsCode().equals(dto.getCode())) {
            log.info("Sms code not valid");
            return ApiResponse.bad("Sms code not valid");
        }
        int result = consultingRepository.changePhone(currentUser.getId(), currentUser.getTempPhone());
        if (result == 0) return ApiResponse.bad("Try again !");

        String jwt = JwtUtil.encode(currentUser.getId(), currentUser.getPhone(), personRoleService.getProfileRoleList(currentUser.getId()));
        return ApiResponse.ok(jwt);
    }

    public ApiResponse<ConsultingDTO> getId(String id) {
        ConsultingEntity entity = get(id);
        return ApiResponse.ok(toDTO(entity));
    }

    public PageImpl<ConsultingDTO> filter(ConsultingFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<ConsultingEntity> filterResultDTO = customRepository.filterPagination(dto, page, size);
        return new PageImpl<>(filterResultDTO.getContent().stream().map(this::toDTO).toList(), pageable, filterResultDTO.getTotalElement());
    }

    public ApiResponse<?> deleted(String id) {
        ConsultingEntity entity = get(id);
        int result = consultingRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }


    private ConsultingDTO toDTO(ConsultingEntity entity) {
        ConsultingDTO dto = new ConsultingDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setAbout(entity.getAbout());
        if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private ConsultingEntity get(String id) {
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} consulting not found", id);
            throw new ItemNotFoundException("Consulting not found");
        }
        return optional.get();
    }

    private ProfileDTO getCurrentConsultingDetail() {
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
}
