package api.scolaro.uz.service.consulting;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.*;
import api.scolaro.uz.dto.profile.UpdatePasswordDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.consulting.ConsultingUniversityEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingRepository;
import api.scolaro.uz.repository.university.UniversityRepository;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.PersonRoleService;
import api.scolaro.uz.service.ResourceMessageService;
import api.scolaro.uz.service.UniversityService;
import api.scolaro.uz.service.sms.SmsHistoryService;
import api.scolaro.uz.util.JwtUtil;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import api.scolaro.uz.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    private final ResourceMessageService resourceMessageService;
    @Autowired
    private UniversityService universityService;

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
            ConsultingEntity entity = optional.get();
            if (!entity.getStatus().equals(GeneralStatus.NOT_ACTIVE)) {
                log.info("Exception : Consulting status is at ACTIVE or BLOCK status {}", dto.getPhone());
                return ApiResponse.bad("Exception : Consulting is at ACTIVE or BLOCK status");
            }
            log.info("Exception : Consulting visible is TRUE {}", dto.getPhone());
            return ApiResponse.bad("Exception : Consulting visible is TRUE");

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
        entity.setFireBaseId(dto.getFireBaseId());
        // save
        consultingRepository.save(entity);
        personRoleService.create(entity.getId(), RoleEnum.ROLE_CONSULTING);
        // response
        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<ConsultingResponseDTO> updateDetail(ConsultingUpdateDTO dto) {
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

    public ApiResponse<ConsultingResponseDTO> updateConsulting(String id, ConsultingUpdateDTO dto) {
        ConsultingEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setAbout(dto.getAbout());
        entity.setOwnerName(dto.getOwnerName());
        entity.setOwnerSurname(dto.getOwnerSurname());
        // update
        consultingRepository.save(entity);
        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<String> changeStatus(String id, GeneralStatus status) {
        ConsultingEntity entity = get(id);
        int result = consultingRepository.changeStatus(entity.getId(), status);
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<String> updatePassword(UpdatePasswordDTO dto) {
        ConsultingDTO currentConsulting = getCurrentConsultingDetail().getData();
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

    public ApiResponse<String> updatePhone(String newPhone) {
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

    public ApiResponse<String> verification(SmsDTO dto) {
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
        ApiResponse<String> smsResponse = smsService.checkSmsCode(dto.getPhone(), dto.getCode());
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

    public ApiResponse<String> deleted(String id) {
        ConsultingEntity entity = get(id);
        int result = consultingRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<ConsultingDTO> getConsultingDetail(String consultingId) {
        ConsultingEntity details = get(consultingId);
        ConsultingDTO consultingDTO = new ConsultingDTO();
        consultingDTO.setId(details.getId());
        consultingDTO.setName(details.getName());
        consultingDTO.setPhone(details.getPhone());
        if (details.getPhotoId() != null) {
            consultingDTO.setPhoto(attachService.getResponseAttach(details.getPhotoId()));
        }
        consultingDTO.setAddress(details.getAddress());
        // get consulting university list
        List<UniversityResponseDTO> oldList = universityService.getConsultingUniversityList(consultingId);
        consultingDTO.setUniversityList(oldList);

        return new ApiResponse<>(200, false, consultingDTO);
    }

    public ApiResponse<ConsultingDTO> getCurrentConsultingDetail() {
        ConsultingEntity details = get(EntityDetails.getCurrentUserId());
        ConsultingDTO currentConsulting = new ConsultingDTO();
        currentConsulting.setId(details.getId());
        currentConsulting.setName(details.getName());
        currentConsulting.setPhone(details.getPhone());
        currentConsulting.setRoleList(personRoleService.getProfileRoleList(details.getId()));
        if (details.getPhotoId() != null) {
            currentConsulting.setPhoto(attachService.getResponseAttach(details.getPhotoId()));
        }
        currentConsulting.setAddress(details.getAddress());
        return new ApiResponse<>(200, false, currentConsulting);
    }

    public ConsultingEntity get(String id) {
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} consulting not found", id);
            throw new ItemNotFoundException("Consulting not found");
        }
        return optional.get();
    }

    public ConsultingResponseDTO getConsultingForApp(String id) {
        ConsultingEntity entity = get(id);
        ConsultingResponseDTO dto = new ConsultingResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwnerName(entity.getOwnerName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setAbout(entity.getAbout());
        dto.setOwnerSurName(entity.getOwnerSurname());
        dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        return dto;
    }

    public ApiResponse<String> deleteAccount() {
        ConsultingEntity entity = get(EntityDetails.getCurrentUserId());
        int result = consultingRepository.deleteAccount(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 1) {
            log.info("Consulting deleted");
            return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.delete"));
        }
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("fail.delete"));
    }

    public ApiResponse<List<ConsultingResponseDTO>> getTopConsulting() {
        List<ConsultingEntity> list = consultingRepository.getTopConsulting();
        List<ConsultingResponseDTO> dtoList = list.stream().map(this::toDTO).toList();
        return new ApiResponse<>(200, false, dtoList);
    }

    public PageImpl<ConsultingResponseDTO> filterForTopConsulting(ConsultingTopFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<ConsultingEntity> filterResultDTO = customRepository.filterPaginationForTopConsulting(dto, page, size);
        return new PageImpl<>(filterResultDTO.getContent().stream().map(this::toDTO).toList(), pageable, filterResultDTO.getTotalElement());
    }

    public List<ConsultingResponseDTO> getUniversityConsultingList(Long universityId) {
        List<ConsultingEntity> universityList = consultingRepository.getUniversityConsultingList(universityId);
        List<ConsultingResponseDTO> dtoList = universityList.stream().map(entity -> {
            ConsultingResponseDTO dto = new ConsultingResponseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setOwnerName(entity.getOwnerName());
            dto.setAddress(entity.getAddress());
            dto.setAbout(entity.getAbout());
            dto.setOwnerSurName(entity.getOwnerSurname());
            if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
            return dto;
        }).toList();
        return dtoList;
    }


    private ConsultingResponseDTO toDTO(ConsultingEntity entity) {
        ConsultingResponseDTO dto = new ConsultingResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwnerName(entity.getOwnerName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setAbout(entity.getAbout());
        dto.setOwnerSurName(entity.getOwnerSurname());
        if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
