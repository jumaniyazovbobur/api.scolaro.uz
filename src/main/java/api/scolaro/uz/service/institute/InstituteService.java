package api.scolaro.uz.service.institute;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.institute.InstituteRequestDTO;
import api.scolaro.uz.dto.institute.InstituteResponseAdminDTO;
import api.scolaro.uz.dto.instituteAttach.InstituteAttachResponseDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationResponseDTO;
import api.scolaro.uz.entity.institute.InstituteEntity;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.institute.InstituteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstituteService {
    private final InstituteRepository repository;
    private final InstituteAttachService instituteAttachService;
    private final InstituteDestinationService instituteDestinationService;

    @Transactional
    public ApiResponse<InstituteResponseAdminDTO> create(InstituteRequestDTO dto) {
        InstituteEntity institute = repository.save(toEntity(dto));
        List<InstituteAttachResponseDTO> attachResponseDTOList = instituteAttachService.merge(institute.getId(), dto.getInstituteAttachRequestDTOS());
        List<InstituteDestinationResponseDTO> destinationResponseDTOList = instituteDestinationService.merge(institute.getId(), dto.getDestinationIds());
        InstituteResponseAdminDTO adminDTO = toDto(institute);
        adminDTO.setInstituteAttachResponseDTOS(attachResponseDTOList);
        adminDTO.setInstituteDestinationResponseDTOS(destinationResponseDTOList);
        return new ApiResponse<>("Institute create successfully", 200, false, adminDTO);
    }

    public ApiResponse<InstituteResponseAdminDTO> update(Long instituteId, InstituteRequestDTO dto) {
        InstituteEntity entity = getId(instituteId);
        InstituteEntity updateEntity = repository.save(updateEntity(dto, entity));
        List<InstituteAttachResponseDTO> attachResponseDTOList = instituteAttachService.merge(instituteId, dto.getInstituteAttachRequestDTOS());
        List<InstituteDestinationResponseDTO> destinationResponseDTOList = instituteDestinationService.merge(instituteId, dto.getDestinationIds());
        InstituteResponseAdminDTO adminDTO = toDto(updateEntity);
        adminDTO.setInstituteAttachResponseDTOS(attachResponseDTOList);
        adminDTO.setInstituteDestinationResponseDTOS(destinationResponseDTOList);
        return new ApiResponse<>("Institute create successfully", 200, false, adminDTO);
    }

    public InstituteEntity toEntity(InstituteRequestDTO dto) {
        InstituteEntity entity = new InstituteEntity();
        entity.setName(dto.getName());
        entity.setCountryId(dto.getCountryId());
        entity.setCityName(dto.getCityName());
        entity.setRating(dto.getRating());
        entity.setAboutUz(dto.getAboutUz());
        entity.setAboutRu(dto.getAboutRu());
        entity.setAboutEn(dto.getAboutEn());
        entity.setTerritoryUz(dto.getTerritoryUz());
        entity.setTerritoryRu(dto.getTerritoryRu());
        entity.setTerritoryEn(dto.getTerritoryEn());
        entity.setEducationUz(dto.getEducationUz());
        entity.setEducationRu(dto.getEducationRu());
        entity.setEducationEn(dto.getEducationEn());
        entity.setPhoneNumber1(dto.getPhoneNumber1());
        entity.setPhoneNumber2(dto.getPhoneNumber2());
        entity.setWebSite(dto.getWebSite());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setFacebookUrl(dto.getFacebookUrl());
        entity.setEmail(dto.getEmail());
        entity.setPhotoId(dto.getPhotoId());
        entity.setCompressedPhotoId(dto.getCompressedPhotoId());
        entity.setLogoId(dto.getLogoId());
        entity.setCompressedLogoId(dto.getCompressedLogoId());
        entity.setMasterDegree(dto.getMasterDegree());
        entity.setMasterDegree(dto.getMasterDegree());
        entity.setStatus(dto.getStatus());
        return entity;
    }
    public InstituteResponseAdminDTO toDto(InstituteEntity entity) {
        InstituteResponseAdminDTO dto = new InstituteResponseAdminDTO();
        dto.setName(entity.getName());
        dto.setCountryId(entity.getCountryId());
        dto.setCityName(entity.getCityName());
        dto.setRating(entity.getRating());
        dto.setAboutUz(entity.getAboutUz());
        dto.setAboutRu(entity.getAboutRu());
        dto.setAboutEn(entity.getAboutEn());
        dto.setTerritoryUz(entity.getTerritoryUz());
        dto.setTerritoryRu(entity.getTerritoryRu());
        dto.setTerritoryEn(entity.getTerritoryEn());
        dto.setEducationUz(entity.getEducationUz());
        dto.setEducationRu(entity.getEducationRu());
        dto.setEducationEn(entity.getEducationEn());
        dto.setPhoneNumber1(entity.getPhoneNumber1());
        dto.setPhoneNumber2(entity.getPhoneNumber2());
        dto.setWebSite(entity.getWebSite());
        dto.setInstagramUrl(entity.getInstagramUrl());
        dto.setFacebookUrl(entity.getFacebookUrl());
        dto.setEmail(entity.getEmail());
        dto.setPhotoId(entity.getPhotoId());
        dto.setCompressedPhotoId(entity.getCompressedPhotoId());
        dto.setLogoId(entity.getLogoId());
        dto.setCompressedLogoId(entity.getCompressedLogoId());
        dto.setMasterDegree(entity.getMasterDegree());
        dto.setMasterDegree(entity.getMasterDegree());
        dto.setStatus(entity.getStatus());
        return dto;
    }
    public InstituteEntity updateEntity(InstituteRequestDTO dto, InstituteEntity entity) {
        entity.setName(dto.getName());
        entity.setCountryId(dto.getCountryId());
        entity.setCityName(dto.getCityName());
        entity.setRating(dto.getRating());
        entity.setAboutUz(dto.getAboutUz());
        entity.setAboutRu(dto.getAboutRu());
        entity.setAboutEn(dto.getAboutEn());
        entity.setTerritoryUz(dto.getTerritoryUz());
        entity.setTerritoryRu(dto.getTerritoryRu());
        entity.setTerritoryEn(dto.getTerritoryEn());
        entity.setEducationUz(dto.getEducationUz());
        entity.setEducationRu(dto.getEducationRu());
        entity.setEducationEn(dto.getEducationEn());
        entity.setPhoneNumber1(dto.getPhoneNumber1());
        entity.setPhoneNumber2(dto.getPhoneNumber2());
        entity.setWebSite(dto.getWebSite());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setFacebookUrl(dto.getFacebookUrl());
        entity.setEmail(dto.getEmail());
        entity.setPhotoId(dto.getPhotoId());
        entity.setCompressedPhotoId(dto.getCompressedPhotoId());
        entity.setLogoId(dto.getLogoId());
        entity.setCompressedLogoId(dto.getCompressedLogoId());
        entity.setMasterDegree(dto.getMasterDegree());
        entity.setMasterDegree(dto.getMasterDegree());
        entity.setStatus(dto.getStatus());
        return entity;
    }



    public InstituteEntity getId(Long id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Institute not found  " + id));
    }
}
