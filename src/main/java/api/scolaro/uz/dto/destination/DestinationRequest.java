package api.scolaro.uz.dto.destination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DestinationRequest(

        Long id,

        @NotBlank(message = "Name Uz  bo'sh bo'lishi mumkin emas")
        @Size(min = 3, max = 50, message = "Berilgan Type (Name Uz) ning uzunligi 3 va 50 orasida bo'lishi kerak")
         String nameUz,

        @NotBlank(message = "Name Ru  bo'sh bo'lishi mumkin emas")
        @Size(min = 3, max = 50, message = "Berilgan Type (Name Ru) ning uzunligi 3 va 50 orasida bo'lishi kerak")
        String nameRu,

        @NotBlank(message = "Name En  bo'sh bo'lishi mumkin emas")
        @Size(min = 3, max = 50, message = "Berilgan Type (Name En) ning uzunligi 3 va 50 orasida bo'lishi kerak")
        String nameEn,

        @NotBlank(message = "Attach id  bo'sh bo'lishi mumkin emas")
        String attachId,

        @NotNull(message = "Type number bo'sh bo'lishi mumkin emas")
        @Min(value = 1, message = "Type number ning qiymati minimal 1 bo'lsin")
        Integer orderNumber
) {
}
