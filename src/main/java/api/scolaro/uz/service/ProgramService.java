package api.scolaro.uz.service;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.program.ProgramFilterDTO;
import api.scolaro.uz.dto.program.ProgramResponseDTO;
import api.scolaro.uz.dto.program.ProgramResponseFilterDTO;
import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.ProgramFilterRepository;
import api.scolaro.uz.repository.ProgramFilterRepositoryFilter;
import api.scolaro.uz.repository.ProgramRepository;
import api.scolaro.uz.service.place.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository repository;
    private final ProgramFilterRepository programFilterRepository;
    private final ProgramFilterRepositoryFilter programFilterRepositoryFilter;
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

    public ApiResponse<ProgramResponseFilterDTO> getById(Long id, AppLanguage language) {
        ProgramEntity entity = getId(id);
        ProgramResponseFilterDTO filterDTO = programFilterRepositoryFilter.getById(id, language.toString());
        return new ApiResponse<>("Program retrieved successfully", 200, false, filterDTO);
    }


    public PageImpl<ProgramResponseFilterDTO> filter(int page, int size, ProgramFilterDTO dto, AppLanguage appLanguage) {
        FilterResultDTO<ProgramResponseFilterDTO> dtoList = programFilterRepositoryFilter.getProgramFilterPage(dto, appLanguage.toString(), page, size);
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(dtoList.getContent(), pageable, dtoList.getTotalElement());
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
                .published(false)
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


    public ProgramEntity getId(Long id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Program not found  " + id));
    }


}
