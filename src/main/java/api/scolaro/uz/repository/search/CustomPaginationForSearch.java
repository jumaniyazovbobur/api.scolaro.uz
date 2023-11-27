package api.scolaro.uz.repository.search;

import api.scolaro.uz.dto.search.SearchResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomPaginationForSearch {

    private List<SearchResponseDTO> university;
    private List<SearchResponseDTO> country;
    private List<SearchResponseDTO> scholar;
    private List<SearchResponseDTO> faculty;
    private List<SearchResponseDTO> consulting;

    private Long totalCount;
}
