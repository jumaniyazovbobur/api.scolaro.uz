package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelResponseDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepResponseDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateResponseDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffResponseDTO;
import api.scolaro.uz.dto.profile.ProfileResponseDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.enums.AppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationResponseDTO {

    private String id;

    private ConsultingResponseDTO consulting;
    private ProfileResponseDTO student;
    private UniversityResponseDTO university;
    private ConsultingTariffResponseDTO tariff;
    private ConsultingStepResponseDTO step;
    private ConsultingStepLevelResponseDTO stepLevel;
    private String studentId;
    private String consultingId;
    private Long universityId;
    private AppStatus status;
    private LocalDateTime createdDate;

}
