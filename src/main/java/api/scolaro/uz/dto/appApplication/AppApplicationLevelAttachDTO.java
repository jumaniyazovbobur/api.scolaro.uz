package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.enums.AttachType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AppApplicationLevelAttachDTO {
    private String id;
    private String consultingStepLevelId;
    private AttachDTO attach;
    private AttachType attachType;
    private LocalDateTime createdDate;
}
