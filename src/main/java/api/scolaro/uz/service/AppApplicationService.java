package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationRequestDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationResponseDTO;
import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.repository.appApplication.AppApplicationFilterRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void filter (AppApplicationFilterDTO filter,int page, int size) {
        appApplicationFilterRepository.filter(filter,page,size);


       /* AppApplicationFilterDTO dto = new AppApplicationFilterDTO();
        dto.setStudentName("Maxmud");
        FilterResultDTO<AppApplicationFilterMapper> filter = appApplicationFilterRepository.filter(dto, 0, 2);
        for (AppApplicationFilterMapper mapper : filter.getContent()) {
            System.out.println(mapper.getSName());
        }*/
    }
}
