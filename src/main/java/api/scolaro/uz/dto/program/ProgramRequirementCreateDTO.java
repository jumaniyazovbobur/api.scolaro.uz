package api.scolaro.uz.dto.program;

import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.enums.StudyMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgramRequirementCreateDTO {

    @NotNull(message = " Program id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Program id ning qiymati minimal 1 bo'lsin")
    private Long programId;

    @NotBlank(message = "Program requirement type  bo'sh bo'lishi mumkin emas")
    private ProgramRequirementType programRequirementType;
}
