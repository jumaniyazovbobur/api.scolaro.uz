package api.scolaro.uz.dto.instituteAttach;

import api.scolaro.uz.enums.InstituteAttachType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidInstituteAttach
public class InstituteAttachRequestDTO {

    @NotNull(message = " Institute id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Institute id ning qiymati minimal 1 bo'lsin")
    private Long instituteId;

    private String attachId;

    private String videoLink;

    @NotNull(message = "instituteAttachType must not be null")
    private InstituteAttachType instituteAttachType;
}
