package api.scolaro.uz.dto.instituteDestination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstituteDestinationRequestDTO {


    @NotNull(message = " Destination id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Destination id ning qiymati minimal 1 bo'lsin")
    private Long destinationId;

    @NotNull(message = " Institute id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Institute id ning qiymati minimal 1 bo'lsin")
    private Long instituteId;
}
