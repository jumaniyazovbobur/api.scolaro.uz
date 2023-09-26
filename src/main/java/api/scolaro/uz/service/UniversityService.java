package api.scolaro.uz.service;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.university.UniversityUpdateDTO;
import api.scolaro.uz.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    public ApiResponse<?> deleted(Long id) {
        return null;
    }

    public ApiResponse<?> update(Long id, UniversityUpdateDTO dto) {
        return null;
    }

    public ApiResponse<?> get(Long id) {
        return null;
    }
}
