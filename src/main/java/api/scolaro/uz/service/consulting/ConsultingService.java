package api.scolaro.uz.service.consulting;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.*;
import api.scolaro.uz.dto.profile.UpdatePasswordDTO;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingRepository;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.PersonRoleService;
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


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultingService {

    private final ConsultingRepository consultingRepository;

    private final CustomConsultingRepository customRepository;

    private final PersonRoleService personRoleService;

    private final AttachService attachService;

    private final SmsHistoryService smsService;

    public ApiResponse<ConsultingResponseDTO> create(ConsultingCreateDTO dto) {
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
        String smsPassword = RandomUtil.getRandomString(6);
        // send sms password
        String text = "Scolaro.uz platformasiga kirish uchun sizning parolingiz: \n" + smsPassword;
        smsService.sendMessage(dto.getPhone(), text, SmsType.CHANGE_PASSWORD, smsPassword);

        ConsultingEntity entity = new ConsultingEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5(smsPassword));
        entity.setAbout(dto.getAbout());
        entity.setOwnerName(dto.getOwnerName());
        entity.setOwnerSurname(dto.getOwnerSurname());
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
        entity.setOwnerName(dto.getOwnerName());
        entity.setOwnerSurname(dto.getOwnerSurname());
        // update
        consultingRepository.save(entity);
        return ApiResponse.ok(toDTO(entity));
    }
    public ApiResponse<?> changeStatus(String id, GeneralStatus status) {
        ConsultingEntity entity = get(id);
        int result = consultingRepository.changeStatus(entity.getId(), status);
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }
    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto) {
        ConsultingDTO currentConsulting = getCurrentConsultingDetail();
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            log.info("Confirmed password is incorrect !");
            return ApiResponse.bad("Confirmed password is incorrect !");
        }
        if (!currentConsulting.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            log.info("Old password error !");
            return ApiResponse.bad("Old password error !");
        }
        int result = consultingRepository.updatePassword(currentConsulting.getId(), MD5Util.getMd5(dto.getNewPassword()));
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<?> updatePhone(String newPhone) {
        if (newPhone.startsWith("+")) {
            newPhone = newPhone.substring(1);
        }
        if (!PhoneUtil.validatePhone(newPhone)) {
            log.info("Phone not valid {}", newPhone);
            return ApiResponse.bad("Phone not valid");
        }
        Optional<ConsultingEntity> optional = consultingRepository.findByPhoneAndVisibleIsTrue(newPhone);
        if (optional.isPresent()) {
            log.info("{} Phone exist", newPhone);
            return ApiResponse.bad("Phone exist !");
        }
        // random sms code
        String smsCode = RandomUtil.getRandomSmsCode();
        consultingRepository.changeNewPhone(EntityDetails.getCurrentUserId(), newPhone, smsCode);
        // send new phone sms code
        String text = "Scolaro.uz tasdiqlash kodi: \n" + smsCode;
        smsService.sendMessage(newPhone, text, SmsType.CHANGE_PHONE, smsCode);
        // response
        return ApiResponse.ok("Tasdiqlash kodi yuborildi.");
    }

    public ApiResponse<?> verification(SmsDTO dto) {
        if (dto.getPhone().startsWith("+")) {
            dto.setPhone(dto.getPhone().substring(1));
        }

        if (!PhoneUtil.validatePhone(dto.getPhone())) {
            log.info("Phone not valid {}", dto.getPhone());
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
        ConsultingEntity currentUser = get(EntityDetails.getCurrentUserId());
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

    public ApiResponse<ConsultingResponseDTO> getId(String id) {
        ConsultingEntity entity = get(id);
        return ApiResponse.ok(toDTO(entity));
    }

    public PageImpl<ConsultingResponseDTO> filter(ConsultingFilterDTO dto, int page, int size) {
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


    private ConsultingResponseDTO toDTO(ConsultingEntity entity) {
        ConsultingResponseDTO dto = new ConsultingResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setAbout(entity.getAbout());
        if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private ConsultingDTO getCurrentConsultingDetail() {
        ConsultingEntity details = get(EntityDetails.getCurrentUserId());
        ConsultingDTO currentConsulting = new ConsultingDTO();
        currentConsulting.setId(details.getId());
        currentConsulting.setName(details.getName());
        currentConsulting.setPhone(details.getPhone());
        currentConsulting.setPassword(details.getPassword());
        return currentConsulting;
    }

    public ConsultingEntity get(String id) {
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} consulting not found", id);
            throw new ItemNotFoundException("Consulting not found");
        }
        return optional.get();
    }
}