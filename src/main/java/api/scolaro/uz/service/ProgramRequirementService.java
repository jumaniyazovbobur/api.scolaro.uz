package api.scolaro.uz.service;

import api.scolaro.uz.repository.ProgramRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramRequirementService {
    private final ProgramRequirementRepository repository;

}
