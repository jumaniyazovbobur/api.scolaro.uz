package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseAttachDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseProfileDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentCreateDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentResponseDTO;
import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.entity.ConsultingCommentEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.repository.ConsultingCommentRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
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
    private final ResourceMessageService resourceMessageService;
    private final AppApplicationRepository appApplicationRepository;
    public ApiResponse<?> create(String consulting_id, ConsultingCommentCreateDTO dto) {
        Optional<ConsultingEntity> consultingEntity = consultingRepository.findByIdAndVisibleTrue(consulting_id);
        if (consultingEntity.isEmpty()){
            log.info("This consulting not found {}", consulting_id);
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.not.found"), 400, true);
        }

        Optional<AppApplicationEntity> appApplication = appApplicationRepository.findByStudentIdAndConsultingId(EntityDetails.getCurrentUserId(), consulting_id);
        if (appApplication.isEmpty()){
            log.info("There is no application between student and consulting {}", consulting_id);
            log.info("There is no application between student and consulting {}", EntityDetails.getCurrentUserId());
            return new ApiResponse<>(resourceMessageService.getMessage("application.not.found"), 400, true);
        }
        AppApplicationEntity appApplicationEntity = appApplication.get();
        if (!appApplicationEntity.getStatus().equals(AppStatus.FINISHED)){
            log.info("App Status not FINISHED");
            return new ApiResponse<>(resourceMessageService.getMessage("application.status.not.FINISHED"), 400, true);
        }

        ConsultingCommentEntity entity = new ConsultingCommentEntity();
        entity.setContent(dto.getContent());
        entity.setConsultingId(consulting_id);
        entity.setProfileId(EntityDetails.getCurrentUserId());

        consultingCommentRepository.save(entity);
        return new ApiResponse<>(200, false, "Success");
    }

    public ApiResponse<List<ConsultingCommentResponseDTO>> getByConsultingId(String consulting_id) {
        List<ConsultingCommentEntity> consultingComment = consultingCommentRepository.getByConsultingId(consulting_id);
        if (consultingComment == null){
            log.info("This email not found {}", consulting_id);
            return new ApiResponse<>(resourceMessageService.getMessage("consulting.not.found"), 400, true);
        }

        List<ConsultingCommentResponseDTO> consultingCommentResponseDTO = new LinkedList<>();
        for (ConsultingCommentEntity consultingCommentEntity : consultingComment) {
            ConsultingCommentResponseDTO commentResponseDTO = new ConsultingCommentResponseDTO();
            commentResponseDTO.setId(consultingCommentEntity.getId());
            commentResponseDTO.setContent(consultingCommentEntity.getContent());
            commentResponseDTO.setCreatedDate(consultingCommentEntity.getCreatedDate());

            ConsultingCommentResponseProfileDTO commentResponseProfileDTO = new ConsultingCommentResponseProfileDTO();
            commentResponseProfileDTO.setId(consultingCommentEntity.getProfileEntity().getId());
            commentResponseProfileDTO.setName(consultingCommentEntity.getProfileEntity().getName());
            commentResponseProfileDTO.setSurname(consultingCommentEntity.getProfileEntity().getSurname());

            commentResponseDTO.setProfileEntity(commentResponseProfileDTO);
            consultingCommentResponseDTO.add(commentResponseDTO);
        }

        return new ApiResponse<>(200, false, consultingCommentResponseDTO);
    }
}
