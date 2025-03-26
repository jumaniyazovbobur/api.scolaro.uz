package api.scolaro.uz.service;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.program.ProgramResponseDTO;
import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.ProgramRepository;
import api.scolaro.uz.service.place.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository repository;
    private final ProgramRequirementService programRequirementService;
    private final DestinationService destinationService;
    private final UniversityService universityService;
    private final AttachService attachService;


    @Transactional
    public ApiResponse<String> create(ProgramCreateDTO request) {
        ProgramEntity programEntity = repository.save(toEntity(request));
        programRequirementService.saveType(programEntity.getId(), request.getProgramRequirementTypes());
        return new ApiResponse<>("Create program : " + programEntity.getId(), 201, false);

    }

    @Transactional
    public ApiResponse<String> update(ProgramCreateDTO request) {
        ProgramEntity entity = getId(request.getId());
        repository.save(updateEntity(request, entity));
        programRequirementService.updateType(request.getId(), request.getProgramRequirementTypes());
        return new ApiResponse<>("Update program : " + request.getId(), 201, false);
    }

    public ApiResponse<String> delete(Long id) {
        ProgramEntity entity = getId(id);
        entity.setVisible(false);
        repository.save(entity);
        programRequirementService.deleteType(id);
        return new ApiResponse<>("Delete program : " + id, 200, false);
    }

    public ApiResponse<List<ProgramResponseDTO>> getAllByLanguage(AppLanguage language) {
        List<ProgramResponseDTO> responseList = new ArrayList<>();
        List<ProgramEntity> programs = repository.findAllByVisibleTrue();
        for (ProgramEntity entity : programs) {
            responseList.add(toDTO(entity, language));
        }
        return new ApiResponse<>("Program retrieved successfully", 200, false, responseList);
    }

    public ApiResponse<ProgramResponseDTO> getById(Long id, AppLanguage language) {
        ProgramEntity entity = getId(id);
        return new ApiResponse<>("Program  retrieved successfully", 200, false, toDTO(entity, language));
    }


    public ProgramEntity toEntity(ProgramCreateDTO request) {
        destinationService.get(request.getDestinationId());
        universityService.get(request.getUniversityId());
        attachService.getEntity(request.getAttachId());

        return ProgramEntity.builder()
                .titleUz(request.getTitleUz())
                .titleRu(request.getTitleRu())
                .titleEn(request.getTitleEn())
                .descriptionUz(request.getDescriptionUz())
                .descriptionRu(request.getDescriptionRu())
                .descriptionEN(request.getDescriptionEN())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .price(request.getPrice())
                .symbol(request.getSymbol())
                .universityId(request.getUniversityId())
                .destinationId(request.getDestinationId())
                .attachId(request.getAttachId())
                .published(request.getPublished())
                .studyFormat(request.getStudyFormat())
                .studyMode(request.getStudyMode())
                .programType(request.getProgramType())
                .build();
    }

    public ProgramEntity updateEntity(ProgramCreateDTO request, ProgramEntity entity) {
        destinationService.get(request.getDestinationId());
        universityService.get(request.getUniversityId());
        attachService.getEntity(request.getAttachId());

        entity.setTitleUz(request.getTitleUz());
        entity.setTitleRu(request.getTitleRu());
        entity.setTitleEn(request.getTitleEn());
        entity.setDescriptionUz(request.getDescriptionUz());
        entity.setDescriptionRu(request.getDescriptionRu());
        entity.setDescriptionEN(request.getDescriptionEN());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setPrice(request.getPrice());
        entity.setSymbol(request.getSymbol());
        entity.setUniversityId(request.getUniversityId());
        entity.setDestinationId(request.getDestinationId());
        entity.setAttachId(request.getAttachId());
        entity.setPublished(request.getPublished());
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
            }
            case uz -> {
                dto.setTitle(entity.getTitleUz());
                dto.setDescription(entity.getDescriptionUz());
            }
            case ru -> {
                dto.setTitle(entity.getTitleRu());
                dto.setDescription(entity.getDescriptionRu());
            }
        }
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDestinationLanguageResponse(destinationService.toDTO(entity.getDestination(),language));
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
