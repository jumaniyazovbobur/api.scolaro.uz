package api.scolaro.uz.dto.destination;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DestinationLanguageResponse(
        Long id,
        String name,
        AttachDTO attach,
        Integer orderNumber
) {
}
