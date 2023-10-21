package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
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
    private String appId;
    private LocalDateTime appCreatedDate;
    private Boolean appVisible;
    private AppStatus appStatus;
    private String conId;

    private String conName;

    private AttachResponseDTO conPhoto;


    private String uniName;

    private Long uniId;

    private AttachResponseDTO uniPhoto;

    private String sId;

    private String sName;
    private String sSurName;

    private AttachResponseDTO sPhoto;
}
