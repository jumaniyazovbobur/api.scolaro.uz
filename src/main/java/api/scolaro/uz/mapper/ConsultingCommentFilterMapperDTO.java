package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseFilterDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.profile.ProfileResponseFilterDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ConsultingCommentFilterMapperDTO {
    private String id;
    private LocalDateTime createdDate;
    private String content;
    private ConsultingResponseFilterDTO consulting;
    private ProfileResponseFilterDTO profile;
}
