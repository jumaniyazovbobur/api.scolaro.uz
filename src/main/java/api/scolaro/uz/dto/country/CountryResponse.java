package api.scolaro.uz.dto.country;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CountryResponse(
        Long id,
        String nameUz,
        String nameRu,
        String nameEn,
        String name,
        AttachDTO attach,
        Integer orderNumber
) {
}
