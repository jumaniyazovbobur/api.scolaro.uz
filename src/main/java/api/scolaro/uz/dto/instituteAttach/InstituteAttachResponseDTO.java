package api.scolaro.uz.dto.instituteAttach;

import api.scolaro.uz.enums.InstituteAttachType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstituteAttachResponseDTO {

    private Long instituteId;

    private String attachId;

    private String videoLink;

    private InstituteAttachType instituteAttachType;
}
