package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentCreateDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentFilterDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseDTO;
import api.scolaro.uz.entity.application.AppApplicationEntity;
import api.scolaro.uz.entity.ConsultingCommentEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.mapper.ConsultingCommentFilterMapperDTO;
import api.scolaro.uz.repository.consulting.ConsultingCommentRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.consulting.CustomConsultingCommentRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultingCommentService {
    private final ConsultingCommentRepository consultingCommentRepository;
    private final CustomConsultingCommentRepository customConsultingCommentRepository;
    private final ConsultingRepository consultingRepository;
    private final ConsultingService consultingService;
    private final ResourceMessageService resourceMessageService;
    private final AppApplicationRepository appApplicationRepository;
    private final AppApplicationService appApplicationService;
    private final ProfileService profileService;
    private final EntityManager entityManager;


    public ApiResponse<?> create(String consulting_id, ConsultingCommentCreateDTO dto) {
        List<AppApplicationEntity> appApplicationList = appApplicationService.getByStudentIdAndConsultingId(EntityDetails.getCurrentUserId(), consulting_id);
        if (appApplicationList.size() == 0) {
            log.info("There is no application between student {} and consulting {}", EntityDetails.getCurrentUserId(), consulting_id);
            return new ApiResponse<>(resourceMessageService.getMessage("application.not.found"), 400, true);
        }
        /*if (!appApplicationEntity.getStatus().equals(AppStatus.FINISHED)) {
            log.info("Application status not FINISHED");
            return new ApiResponse<>(resourceMessageService.getMessage("application.status.not.FINISHED"), 400, true);
        }*/
        ConsultingCommentEntity entity = new ConsultingCommentEntity();
        entity.setContent(dto.getContent());
        entity.setConsultingId(consulting_id);
        entity.setStudentId(EntityDetails.getCurrentUserId());
        consultingCommentRepository.save(entity);
        return new ApiResponse<>(200, false, "Success");
    }

    public ApiResponse<Page<ConsultingCommentResponseDTO>> getByConsultingId(String consulting_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate"));
        Page<ConsultingCommentEntity> pageResult = consultingCommentRepository.getByConsultingIdAndVisibleTrue(consulting_id, pageable);

        List<ConsultingCommentResponseDTO> consultingCommentResponseDTO = new LinkedList<>();
        for (ConsultingCommentEntity entity : pageResult.getContent()) {
            ConsultingCommentResponseDTO response = new ConsultingCommentResponseDTO();
            response.setId(entity.getId());
            response.setContent(entity.getContent());
            response.setCreatedDate(entity.getCreatedDate());
            response.setStudent(profileService.getProfileInfo(entity.getStudentId()));
            consultingCommentResponseDTO.add(response);
        }
        Page<ConsultingCommentResponseDTO> pageObj = new PageImpl<>(consultingCommentResponseDTO, pageable, pageResult.getTotalElements());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<ConsultingCommentFilterMapperDTO>> filterForAdmin(ConsultingCommentFilterDTO filter, int page, int size) {
        FilterResultDTO<ConsultingCommentFilterMapperDTO> filterResult = customConsultingCommentRepository.filterForAdmin(filter, page, size);
        Page<ConsultingCommentFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public void delete(String consultingCommentId) {
        consultingCommentRepository.deleteConsultingCommentId(consultingCommentId);
    }
}
