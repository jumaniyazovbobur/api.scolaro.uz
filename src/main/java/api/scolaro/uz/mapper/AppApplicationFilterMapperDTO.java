package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.application.AppApplicationLevelStatusEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationFilterMapperDTO {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime startedDate;
    private AppStatus status;
    private ConsultingDTO consulting;
    private UniversityResponseDTO university;
    private ProfileDTO student;
    private ApplicationStepLevelStatus applicationStepLevelStatus;
    private Long applicationNumber;
    private ConsultingStepLevelDTO consultingStepLevel;
    private AppApplicationLevelStatusDTO appApplicationLevelStatus;
}
