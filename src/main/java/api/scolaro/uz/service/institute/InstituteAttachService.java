package api.scolaro.uz.service.institute;

import api.scolaro.uz.dto.instituteAttach.InstituteAttachRequestDTO;
import api.scolaro.uz.dto.instituteAttach.InstituteAttachResponseDTO;
import api.scolaro.uz.entity.ProgramRequirementEntity;
import api.scolaro.uz.entity.institute.InstituteAttachEntity;
import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.repository.institute.InstituteAttachRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstituteAttachService {
    private final InstituteAttachRepository repository;


    public List<InstituteAttachResponseDTO> merge(Long instituteId, List<InstituteAttachRequestDTO> newList) {
        if (newList == null) {
            newList = new LinkedList<>();
        }
        repository.deleteByInstituteId(instituteId);
        List<InstituteAttachEntity> entities = repository.saveAll(create(instituteId, newList));
        return entities.stream().map(this::toDTO).toList();
    }


    public List<InstituteAttachEntity> create(Long instituteId, List<InstituteAttachRequestDTO> dtoList) {
        List<InstituteAttachEntity> entities = new LinkedList<>();
        for (InstituteAttachRequestDTO dto : dtoList) {
            InstituteAttachEntity entity = new InstituteAttachEntity();
            entity.setInstituteId(instituteId);
            entity.setAttachId(dto.getAttachId());
            entity.setVideoLink(dto.getVideoLink());
            entity.setInstituteAttachtype(dto.getInstituteAttachType());
            entities.add(entity);
        }
        return entities;
    }

    public List<InstituteAttachResponseDTO> getAll(Long instituteId) {
        List<InstituteAttachEntity> entities = repository.findByInstituteId(instituteId);
        return entities.stream().map(this::toDTO).toList();
    }

    public InstituteAttachResponseDTO toDTO(InstituteAttachEntity entity) {
        InstituteAttachResponseDTO dto = new InstituteAttachResponseDTO();
            dto.setInstituteId(entity.getInstituteId());
            dto.setAttachId(entity.getAttachId());
            dto.setVideoLink(entity.getVideoLink());
            dto.setInstituteAttachType(entity.getInstituteAttachtype());
        return dto;
    }

}
