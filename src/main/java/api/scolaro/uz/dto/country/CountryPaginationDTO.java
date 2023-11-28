package api.scolaro.uz.dto.country;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// PROJECT NAME -> api.dachatop
// TIME -> 23:46
// MONTH -> 08
// DAY -> 12
@Getter
@Setter
public class CountryPaginationDTO {
    private long totalElements;
    private int totalPages;
    private List<CountryResponseDTO> content;

    public CountryPaginationDTO(long totalElements, int totalPages, List<CountryResponseDTO> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }
}
