package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingCreateDTO;
import api.scolaro.uz.entity.ConsultingEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingRepository;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
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


    public ConsultingDTO create(ConsultingCreateDTO dto) {
        if (!PhoneUtil.validatePhone(dto.getPhone())) {
            log.warn("Exception : Phone not format");
            throw new AppBadRequestException("Phone not format");
        }
        // TODO generate phone (5 raqamli sonlar va harflar ) (0oO)
        //TODO send password as SMS ?
        //
        ConsultingEntity entity = new ConsultingEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        entity.setStatus(GeneralStatus.ACTIVE);
//        entity.setRole(RoleEnum.ROLE_CONSULTING);
        // save
        consultingRepository.save(entity);
        personRoleService.create(entity.getId(), RoleEnum.ROLE_CONSULTING);
        // response
        return toDTO(entity);
    }


    public ConsultingDTO update(ConsultingCreateDTO dto) {
        //TODO PHONE SMS SEND ?
        String id = EntityDetails.getCurrentUserId();
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);

        ConsultingEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        // update
        consultingRepository.save(entity);
        // response
        return toDTO(entity);
    }

    // TODO api for update password (old and newPassword , confirmNewPassword)


    public ConsultingDTO updatePhone(String tempPhone) {
        // TODO update hone throw sms verification
        // setTempPhone(tempPhone)
        // 1. send sms to tempPhone
        // 2. confirm alohida api
        return null;
    }

    public ConsultingDTO getId(String id) {
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} consulting not found", id);
            throw new ItemNotFoundException("Not found");
        }
        return toDTO(optional.get());
    }

    public PageImpl<ConsultingDTO> filter(ConsultingFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<ConsultingEntity> filterResultDTO = customRepository.filterPagination(dto, page, size);
        return new PageImpl<>(filterResultDTO.getContent().stream().map(this::toDTO).toList(), pageable, filterResultDTO.getTotalElement());
    }

    public ConsultingDTO deleted(String id) {
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("Exception : {} consulting not found", id);
            throw new ItemNotFoundException("Not found");
        }
        // deletedId; // TODO use
        consultingRepository.deleted(id, LocalDateTime.now());
        return toDTO(optional.get());

    }

    // TODO getCurrentConsultingDetail()  (id,name,surname,phone, description, phone)


    private ConsultingDTO toDTO(ConsultingEntity entity) {
        ConsultingDTO dto = new ConsultingDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
