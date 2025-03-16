package api.scolaro.uz.dto.countryFlag;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CountryFlagResponse(

        String id,
        String nameUz,
        String nameRu,
        String nameEn,
        String name,
        AttachDTO attach,
        Integer orderNumber
) {
}
