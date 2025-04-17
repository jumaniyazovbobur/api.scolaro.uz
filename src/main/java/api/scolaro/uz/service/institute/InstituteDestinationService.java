package api.scolaro.uz.service.institute;

import api.scolaro.uz.dto.instituteAttach.InstituteAttachRequestDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationRequestDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationResponseDTO;
import api.scolaro.uz.entity.institute.InstituteAttachEntity;
import api.scolaro.uz.entity.institute.InstituteDestinationEntity;
import api.scolaro.uz.repository.institute.InstituteDestinationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstituteDestinationService {
    private final InstituteDestinationRepository repository;

    public List<InstituteDestinationResponseDTO> merge(Long instituteId, List<InstituteDestinationRequestDTO> newList) {
        if (newList == null) {
            newList = new LinkedList<>();
        }
        repository.deleteByInstituteId(instituteId);
        List<InstituteDestinationEntity> entities = repository.saveAll(create(instituteId, newList));
        return entities.stream().map(this::toDTO).toList();
    }


    public List<InstituteDestinationEntity> create(Long instituteId, List<InstituteDestinationRequestDTO> dtoList) {
        List<InstituteDestinationEntity> entities = new LinkedList<>();
        for (InstituteDestinationRequestDTO dto : dtoList) {
            InstituteDestinationEntity entity = new InstituteDestinationEntity();
            entity.setInstituteId(instituteId);
            entity.setDestinationId(dto.getDestinationId());
            entities.add(entity);
        }
        return entities;
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