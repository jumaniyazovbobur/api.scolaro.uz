package api.scolaro.uz.service.institute;

import api.scolaro.uz.dto.instituteAttach.InstituteAttachRequestDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationRequestDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationResponseDTO;
import api.scolaro.uz.entity.ProgramRequirementEntity;
import api.scolaro.uz.entity.institute.InstituteAttachEntity;
import api.scolaro.uz.entity.institute.InstituteDestinationEntity;
import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.repository.institute.InstituteDestinationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstituteDestinationService {
    private final InstituteDestinationRepository repository;

    public List<InstituteDestinationResponseDTO> merge(Long instituteId, List<Long> destinationIds) {
        if (destinationIds == null) {
            destinationIds = new LinkedList<>();
        }
        repository.deleteByInstituteId(instituteId);
        List<InstituteDestinationEntity> entities = repository.saveAll(create(instituteId, destinationIds));
        return entities.stream().map(this::toDTO).toList();
    }


    public List<InstituteDestinationEntity> create(Long instituteId, List<Long> destinationIds) {
        return destinationIds.stream()
                .map(destinationId -> {
                    InstituteDestinationEntity entity = new InstituteDestinationEntity();
                    entity.setInstituteId(instituteId);
                    entity.setDestinationId(destinationId);
                    return entity;
                })
                .collect(Collectors.toList());
    }

    public List<InstituteDestinationResponseDTO> getAll(Long instituteId) {
        List<InstituteDestinationEntity> entities = repository.findByInstituteId(instituteId);
        return entities.stream().map(this::toDTO).toList();
    }

    public InstituteDestinationResponseDTO toDTO(InstituteDestinationEntity entity) {
        InstituteDestinationResponseDTO dto = new InstituteDestinationResponseDTO();
        dto.setInstituteId(entity.getInstituteId());
        dto.setDestinationId(entity.getDestinationId());
        return dto;
    }
}