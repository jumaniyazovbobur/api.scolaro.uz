package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingRegDTO;
import api.scolaro.uz.entity.ConsultingEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingRepository;
import api.scolaro.uz.util.MD5Util;
import api.scolaro.uz.util.PhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
public class ConsultingService {
    @Autowired
    private ConsultingRepository consultingRepository;
    @Autowired
    private CustomConsultingRepository customRepository;


    public ConsultingDTO create(ConsultingRegDTO dto) {
        if (!PhoneUtil.validatePhone(dto.getPhone())) {
            log.warn("Exception : Phone not format");
            throw new AppBadRequestException("Phone not format");
        }


        //TODO  PHONE  SMS SEND ?


        ConsultingEntity entity = new ConsultingEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        entity.setStatus(GeneralStatus.REG);
        // save
        consultingRepository.save(entity);
        // response
        return toDTO(entity);
    }


    public ConsultingDTO update(ConsultingRegDTO dto) {
        //TODO PHONE SMS SEND ?
        String id = EntityDetails.getCurrentUserId();
        Optional<ConsultingEntity> optional = consultingRepository.findByIdAndVisibleTrue(id);

        ConsultingEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        // update
        consultingRepository.save(entity);
        // response
        return toDTO(entity);
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
        consultingRepository.deleted(id, LocalDateTime.now());
        return toDTO(optional.get());

    }

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
