package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseAttachDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseProfileDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentCreateDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.entity.ConsultingCommentEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.repository.ConsultingCommentRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultingCommentService {
    private final ConsultingCommentRepository consultingCommentRepository;
    private final ConsultingRepository consultingRepository;
    private final ConsultingService consultingService;
    private final ResourceMessageService resourceMessageService;
    private final AppApplicationRepository appApplicationRepository;
    private final AppApplicationService appApplicationService;
    private final ProfileService profileService;


    public ApiResponse<?> create(String consulting_id, ConsultingCommentCreateDTO dto) {
        AppApplicationEntity appApplicationEntity = appApplicationService.getByStudentIdAndConsultingId(EntityDetails.getCurrentUserId(), consulting_id);
        if (appApplicationEntity == null) {
            log.info("There is no application between student {} and consulting {}", EntityDetails.getCurrentUserId(), consulting_id);
            return new ApiResponse<>(resourceMessageService.getMessage("application.not.found"), 400, true);
        }
        if (!appApplicationEntity.getStatus().equals(AppStatus.FINISHED)) {
            log.info("Application status not FINISHED");
            return new ApiResponse<>(resourceMessageService.getMessage("application.status.not.FINISHED"), 400, true);
        }
        ConsultingCommentEntity entity = new ConsultingCommentEntity();
        entity.setContent(dto.getContent());
        entity.setConsultingId(consulting_id);
        entity.setStudentId(EntityDetails.getCurrentUserId());
        consultingCommentRepository.save(entity);
        return new ApiResponse<>(200, false, "Success");
    }

    public ApiResponse<List<ConsultingCommentResponseDTO>> getByConsultingId(String consulting_id) {
        List<ConsultingCommentEntity> consultingComment = consultingCommentRepository.getByConsultingId(consulting_id);
        List<ConsultingCommentResponseDTO> consultingCommentResponseDTO = new LinkedList<>();
        for (ConsultingCommentEntity entity : consultingComment) {
            ConsultingCommentResponseDTO response = new ConsultingCommentResponseDTO();
            response.setId(entity.getId());
            response.setContent(entity.getContent());
            response.setCreatedDate(entity.getCreatedDate());
            response.setStudent(profileService.getProfileInfo(entity.getStudentId()));
        }
        return new ApiResponse<>(200, false, consultingCommentResponseDTO);
    }
}
