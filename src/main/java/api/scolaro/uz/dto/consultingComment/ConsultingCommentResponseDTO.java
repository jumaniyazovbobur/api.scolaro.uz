package api.scolaro.uz.dto.consultingComment;

import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.profile.ProfileResponseDTO;
import api.scolaro.uz.entity.AttachEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultingCommentResponseDTO {
    private String id;
    private String content;
    private LocalDateTime createdDate;
    private ProfileResponseDTO student;
    private ConsultingDTO consulting;

}
