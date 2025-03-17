package api.scolaro.uz.dto.destination;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DestinationResponse(
        Long id,
        String nameUz,
        String nameRu,
        String nameEn,
        String name,
        AttachDTO attach,
        Integer orderNumber
) {
}
