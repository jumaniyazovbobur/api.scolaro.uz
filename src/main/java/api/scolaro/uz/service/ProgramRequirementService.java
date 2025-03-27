package api.scolaro.uz.service;

import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.entity.ProgramRequirementEntity;
import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.repository.ProgramRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramRequirementService {
    private final ProgramRequirementRepository repository;


    public void merge(Long programId, List<ProgramRequirementType> newList) {
        if (newList == null) {
            newList = new LinkedList<>();
        }
        List<ProgramRequirementType> oldList = repository.findRequirementTypeListByProgramId(programId);
        // create
        newList.stream()
                .filter(newRole -> !oldList.contains(newRole))
                .forEach(newRole -> create(programId, newRole));
        // delete
        List<ProgramRequirementType> finalNewList = newList;
        oldList.stream()
                .filter(oldRole -> !finalNewList.contains(oldRole))
                .forEach(oldRole -> delete(programId, oldRole));
    }

    public void create(Long programId, ProgramRequirementType type) {
        ProgramRequirementEntity entity = new ProgramRequirementEntity();
        entity.setProgramId(programId);
        entity.setType(type);
        repository.save(entity);
    }

    public void delete(Long programId, ProgramRequirementType type) {
        repository.deleteByProgramIdAndRequirementType(programId, type);
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
