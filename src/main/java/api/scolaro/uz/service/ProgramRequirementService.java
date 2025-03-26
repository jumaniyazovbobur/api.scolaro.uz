package api.scolaro.uz.service;

import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.entity.ProgramRequirementEntity;
import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.repository.ProgramRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramRequirementService {
    private final ProgramRequirementRepository repository;


    public void saveType(Long id, List<ProgramRequirementType> types) {
        repository.saveAll(toEntity(id, types));
    }

    public void updateType(Long id, List<ProgramRequirementType> types) {
        deleteType(id);
        repository.saveAll(toEntity(id, types));
    }

    public void deleteType(Long id) {
        repository.deleteAllByProgramId(id);
    }



    public List<ProgramRequirementType> typeList(Long id) {
        return repository.findAllByProgramId(id).stream().map(ProgramRequirementEntity::getType).toList();
    }

    public List<ProgramRequirementEntity> toEntity(Long id, List<ProgramRequirementType> types) {
        List<ProgramRequirementEntity> entities = new ArrayList<>();
        for (ProgramRequirementType type : types) {
            ProgramRequirementEntity entity = new ProgramRequirementEntity();
            entity.setProgramId(id);
            entity.setType(type);
            entities.add(entity);
        }
        return entities;
    }


}
