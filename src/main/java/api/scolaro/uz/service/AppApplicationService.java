package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationRequestDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationResponseDTO;
import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.repository.appApplication.AppApplicationFilterRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppApplicationService {

    private final ConsultingService consultingService;
    private final UniversityService universityService;
    private final AppApplicationRepository appApplicationRepository;
    private final AppApplicationFilterRepository appApplicationFilterRepository;

    public ApiResponse<?> create(AppApplicationRequestDTO dto) {

        ConsultingEntity consulting = consultingService.get(dto.getConsultingId());
        UniversityEntity university = universityService.get(dto.getUniversityId());

        AppApplicationEntity entity = new AppApplicationEntity();
        entity.setStudentId(EntityDetails.getCurrentUserId());
        entity.setConsultingId(consulting.getId());
        entity.setUniversityId(university.getId());
        entity.setStatus(AppStatus.TRAIL);

        appApplicationRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

//    public ApiResponse<?> filter(AppApplicationFilterDTO dto)
    public AppApplicationResponseDTO toDTO(AppApplicationEntity entity) {
        AppApplicationResponseDTO dto = new AppApplicationResponseDTO();
        dto.setConsultingId(entity.getConsultingId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUniversityId(entity.getUniversityId());
        dto.setStudentId(entity.getStudentId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public ApiResponse<?> filterForAdmin (AppApplicationFilterDTO filter,int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.filterForAdmin(filter,page,size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<?> filterForStudent (int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getForStudent(page,size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }
}
