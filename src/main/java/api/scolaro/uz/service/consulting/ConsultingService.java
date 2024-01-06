package api.scolaro.uz.service.consulting;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.*;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.enums.sms.SmsType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingProfileRepository;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingRepository;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.PersonRoleService;
import api.scolaro.uz.service.UniversityService;
import api.scolaro.uz.service.sms.SmsHistoryService;
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
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultingService {

    private final ConsultingRepository consultingRepository;

    private final CustomConsultingRepository customRepository;

    private final PersonRoleService personRoleService;
    @Autowired
    private AttachService attachService;

    private final SmsHistoryService smsService;
    private final ConsultingProfileRepository consultingProfileRepository;
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
        Optional<ConsultingProfileEntity> optional = consultingProfileRepository.findByPhoneAndVisibleIsTrue(dto.getPhone());
        if (optional.isPresent()) {
            ConsultingProfileEntity entity = optional.get();
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

        ConsultingEntity consultingEntity = new ConsultingEntity();
        consultingEntity.setName(dto.getName());
        consultingEntity.setAddress(dto.getAddress());
        consultingEntity.setPhotoId(dto.getPhotoId());
        consultingEntity.setStatus(GeneralStatus.ACTIVE);
        consultingEntity.setAbout(dto.getAbout());
        consultingEntity.setBalance(0L);
        consultingRepository.save(consultingEntity);// save

        ConsultingProfileEntity consultingProfile = new ConsultingProfileEntity();
        consultingProfile.setName(dto.getOwnerName());
        consultingProfile.setSurname(dto.getOwnerSurname());
        consultingProfile.setPhone(dto.getPhone());
        consultingProfile.setPassword(MD5Util.getMd5(smsPassword));
        consultingProfile.setStatus(GeneralStatus.ACTIVE);
        consultingProfile.setConsultingId(consultingEntity.getId());
        consultingProfileRepository.save(consultingProfile); // save
        personRoleService.create(consultingProfile.getId(), RoleEnum.ROLE_CONSULTING); // add role
        personRoleService.create(consultingProfile.getId(), RoleEnum.ROLE_CONSULTING_MANAGER); // add role
        consultingRepository.updateManager(consultingEntity.getId(), consultingProfile.getId());// update consulting manager id
        // response
        return ApiResponse.ok();
    }

    public ApiResponse<ConsultingResponseDTO> updateDetail(ConsultingUpdateDTO dto) {
        ConsultingEntity entity = get(EntityDetails.getCurrentUserDetail().getProfileConsultingId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setAbout(dto.getAbout());
        // update
        consultingRepository.save(entity);
        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<ConsultingResponseDTO> updateConsulting(String id, ConsultingUpdateDTO dto) {
        ConsultingEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setAbout(dto.getAbout());
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

    public ApiResponse<ConsultingResponseDTO> getId(String id) {
        ConsultingEntity entity = get(id);
        return ApiResponse.ok(toDTO(entity));
    }

    public ConsultingResponseDTO getById(String id) {
        ConsultingEntity entity = get(id);
        return toDTO(entity);
    }

    public PageImpl<ConsultingResponseDTO> filter(ConsultingFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<ConsultingEntity> filterResultDTO = customRepository.filterPagination(dto, page, size);
        return new PageImpl<>(filterResultDTO.getContent().stream().map(this::toDTO).toList(), pageable, filterResultDTO.getTotalElement());
    }

//    public ApiResponse<String> deleted(String id) {
//        ConsultingEntity entity = get(id);
//        int result = consultingRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
//        if (result == 0) return ApiResponse.bad("Try again !");
//        return ApiResponse.ok("Success");
//    }

    public ApiResponse<ConsultingDTO> getConsultingDetail(String consultingId) {
        ConsultingEntity details = get(consultingId);
        ConsultingDTO consultingDTO = new ConsultingDTO();
        consultingDTO.setId(details.getId());
        consultingDTO.setName(details.getName());
        consultingDTO.setAddress(details.getAddress());
        if (details.getPhotoId() != null) {
            consultingDTO.setPhoto(attachService.getResponseAttach(details.getPhotoId()));
        }
        consultingDTO.setAddress(details.getAddress());
        consultingDTO.setAbout(details.getAbout());
        // get consulting university list
        List<UniversityResponseDTO> oldList = universityService.getConsultingUniversityList(consultingId);
        consultingDTO.setUniversityList(oldList);

        return new ApiResponse<>(200, false, consultingDTO);
    }

    // TODO remove
    public ApiResponse<ConsultingDTO> getCurrentConsultingDetail() {
        ConsultingEntity details = get(EntityDetails.getCurrentUserDetail().getProfileConsultingId());
        ConsultingDTO currentConsulting = new ConsultingDTO();
        currentConsulting.setId(details.getId());
        currentConsulting.setName(details.getName());
        if (details.getPhotoId() != null) {
            currentConsulting.setPhoto(attachService.getResponseAttach(details.getPhotoId()));
        }
        currentConsulting.setAddress(details.getAddress());
        return new ApiResponse<>(200, false, currentConsulting);
    }

    public ConsultingResponseDTO getConsultingForApp(String id) {
        ConsultingEntity entity = get(id);
        ConsultingResponseDTO dto = new ConsultingResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setAbout(entity.getAbout());
        dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        return dto;
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
            dto.setAddress(entity.getAddress());
            dto.setAbout(entity.getAbout());
            if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
            return dto;
        }).toList();
        return dtoList;
    }

    public ConsultingEntity get(String id) {
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info(" {} consulting not found", id);
            throw new ItemNotFoundException("Consulting not found");
        }
        return optional.get();
    }

    private ConsultingResponseDTO toDTO(ConsultingEntity entity) {
        ConsultingResponseDTO dto = new ConsultingResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setAbout(entity.getAbout());
        if (entity.getPhotoId() != null) dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public void fillConsultingBalance(String consultingId, Long amount) {
        consultingRepository.fillBalance(consultingId, amount);
    }
}
