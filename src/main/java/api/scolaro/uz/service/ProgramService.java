package api.scolaro.uz.service;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.program.ProgramFilterDTO;
import api.scolaro.uz.dto.program.ProgramResponseDTO;
import api.scolaro.uz.dto.university.UniversityResponseFilterDTO;
import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.ProgramFilterRepository;
import api.scolaro.uz.repository.ProgramRepository;
import api.scolaro.uz.service.place.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository repository;
    private final ProgramFilterRepository programFilterRepository;
    private final ProgramRequirementService programRequirementService;
    private final DestinationService destinationService;
    private final UniversityService universityService;
    private final AttachService attachService;


    @Transactional
    public ApiResponse<String> create(ProgramCreateDTO request) {
        ProgramEntity programEntity = repository.save(toEntity(request));
        programRequirementService.merge(programEntity.getId(), request.getProgramRequirementTypes());
        return new ApiResponse<>("Create program : " + programEntity.getId(), 201, false);
    }

    @Transactional
    public ApiResponse<String> update(ProgramCreateDTO request) {
        ProgramEntity entity = getId(request.getId());
        repository.save(updateEntity(request, entity));
        programRequirementService.merge(request.getId(), request.getProgramRequirementTypes());
        return new ApiResponse<>("Update program : " + request.getId(), 200, false);
    }

    public ApiResponse<String> delete(Long id) {
        ProgramEntity entity = getId(id);
        entity.setVisible(false);
        repository.save(entity);
        return new ApiResponse<>("Delete program : " + id, 200, false);
    }

    public ApiResponse<String> publishBlock(Long id, Boolean published) {
        ProgramEntity entity = getId(id);
        entity.setPublished(published);
        repository.save(entity);
        return new ApiResponse<>("Publish program : " + id, 200, false);
    }

    public ApiResponse<ProgramResponseDTO> getById(Long id, AppLanguage language) {
        ProgramEntity entity = getId(id);
        return new ApiResponse<>("Program retrieved successfully", 200, false, toDTO(entity,language));
    }


    public PageImpl<ProgramResponseDTO> filter(int page, int size, ProgramFilterDTO dto, AppLanguage appLanguage) {
        FilterResultDTO<ProgramEntity> programFilterPage = programFilterRepository.getProgramFilterPage(dto, page, size);
        List<ProgramResponseDTO> dtoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        for (ProgramEntity entity:programFilterPage.getContent()){
            ProgramResponseDTO programResponseDTO = toDTO(entity,appLanguage);
            dtoList.add(programResponseDTO);
        }
        return new PageImpl<>(dtoList, pageable, programFilterPage.getTotalElement());
    }



    public ProgramEntity toEntity(ProgramCreateDTO request) {
        return ProgramEntity.builder()
                .titleUz(request.getTitleUz())
                .titleRu(request.getTitleRu())
                .titleEn(request.getTitleEn())
                .descriptionUz(request.getDescriptionUz())
                .descriptionRu(request.getDescriptionRu())
                .descriptionEN(request.getDescriptionEN())
                .tuitionFeesDescriptionUz(request.getTuitionFeesDescriptionUz())
                .tuitionFeesDescriptionRu(request.getTuitionFeesDescriptionRu())
                .tuitionFeesDescriptionEn(request.getTuitionFeesDescriptionEn())
                .scholarshipDescriptionUz(request.getScholarshipDescriptionUz())
                .scholarshipDescriptionRu(request.getScholarshipDescriptionRu())
                .scholarshipDescriptionEn(request.getScholarshipDescriptionEn())
                .costOfLivingDescriptionUz(request.getCostOfLivingDescriptionUz())
                .costOfLivingDescriptionRu(request.getCostOfLivingDescriptionRu())
                .costOfLivingDescriptionEn(request.getCostOfLivingDescriptionEn())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .price(request.getPrice())
                .symbol(request.getSymbol())
                .universityId(request.getUniversityId())
                .destinationId(request.getDestinationId()) // tipa kategoriya
                .attachId(request.getAttachId())
                .studyFormat(request.getStudyFormat())
                .studyMode(request.getStudyMode())
                .programType(request.getProgramType())
                .build();

    }

    public ProgramEntity updateEntity(ProgramCreateDTO request, ProgramEntity entity) {

        entity.setTitleUz(request.getTitleUz());
        entity.setTitleRu(request.getTitleRu());
        entity.setTitleEn(request.getTitleEn());
        entity.setDescriptionUz(request.getDescriptionUz());
        entity.setDescriptionRu(request.getDescriptionRu());
        entity.setDescriptionEN(request.getDescriptionEN());
        entity.setTuitionFeesDescriptionUz(request.getTuitionFeesDescriptionUz());
        entity.setTuitionFeesDescriptionRu(request.getTuitionFeesDescriptionRu());
        entity.setTuitionFeesDescriptionEn(request.getTuitionFeesDescriptionEn());
        entity.setScholarshipDescriptionUz(request.getScholarshipDescriptionUz());
        entity.setScholarshipDescriptionRu(request.getScholarshipDescriptionRu());
        entity.setScholarshipDescriptionEn(request.getScholarshipDescriptionEn());
        entity.setCostOfLivingDescriptionUz(request.getCostOfLivingDescriptionUz());
        entity.setCostOfLivingDescriptionRu(request.getCostOfLivingDescriptionRu());
        entity.setCostOfLivingDescriptionEn(request.getCostOfLivingDescriptionEn());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setPrice(request.getPrice());
        entity.setSymbol(request.getSymbol());
        entity.setUniversityId(request.getUniversityId());
        entity.setDestinationId(request.getDestinationId());
        entity.setAttachId(request.getAttachId());
        entity.setStudyFormat(request.getStudyFormat());
        entity.setStudyMode(request.getStudyMode());
        entity.setProgramType(request.getProgramType());
        return entity;
    }

    public ProgramResponseDTO toDTO(ProgramEntity entity, AppLanguage language) {
        if (entity == null) return null;
        ProgramResponseDTO dto = new ProgramResponseDTO();
        dto.setId(entity.getId());

        switch (language) {
            case en -> {
                dto.setTitle(entity.getTitleEn());
                dto.setDescription(entity.getDescriptionEN());
                dto.setTuitionFeesDescription(entity.getTuitionFeesDescriptionEn());
                dto.setScholarshipDescription(entity.getScholarshipDescriptionEn());
                dto.setCostOfLivingDescription(entity.getCostOfLivingDescriptionEn());
            }
            case uz -> {
                dto.setTitle(entity.getTitleUz());
                dto.setDescription(entity.getDescriptionUz());
                dto.setTuitionFeesDescription(entity.getTuitionFeesDescriptionUz());
                dto.setScholarshipDescription(entity.getScholarshipDescriptionUz());
                dto.setCostOfLivingDescription(entity.getCostOfLivingDescriptionUz());
            }
            case ru -> {
                dto.setTitle(entity.getTitleRu());
                dto.setDescription(entity.getDescriptionRu());
                dto.setTuitionFeesDescription(entity.getTuitionFeesDescriptionRu());
                dto.setScholarshipDescription(entity.getScholarshipDescriptionRu());
                dto.setCostOfLivingDescription(entity.getCostOfLivingDescriptionRu());
            }
        }
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStudyFormat(entity.getStudyFormat());
        dto.setStudyMode(entity.getStudyMode());
        dto.setProgramType(entity.getProgramType());
        dto.setPrice(entity.getPrice());
        dto.setSymbol(entity.getSymbol());
        dto.setDestinationLanguageResponse(destinationService.toDTO(entity.getDestination(), language));
        dto.setUniversityResponse(universityService.toDTO(entity.getUniversity(), language));
        dto.setAttach(attachService.getResponseAttach(entity.getAttachId()));
        dto.setProgramRequirementTypes(programRequirementService.typeList(entity.getId()));
        return dto;
    }


    public ProgramEntity getId(Long id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Program not found  " + id));
    }


}
