package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
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
public class AppApplicationFilterMapperDTO {
    private String id;
    private LocalDateTime createdDate;
    private AppStatus status;
    private ConsultingDTO consulting;
    private UniversityResponseDTO university;
    private ProfileDTO student;
}
