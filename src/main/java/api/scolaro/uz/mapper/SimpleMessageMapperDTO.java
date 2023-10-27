package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SimpleMessageMapperDTO {
    private String id;
    private LocalDateTime createdDate;
    private String applicationId;
    private AttachDTO attachDTO;
    private String consultingId;
    private String studentId;
    private Boolean isStudentRead;
    private Boolean isConsultingRead;
    private String message;
    private MessageType messageType;
}
