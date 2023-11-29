package api.scolaro.uz.repository.search;

import api.scolaro.uz.dto.search.SearchResponseDTO;
import api.scolaro.uz.dto.CustomPaginationDTO;
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

    private CustomPaginationDTO<SearchResponseDTO> university;
    private List<SearchResponseDTO> country;
    private CustomPaginationDTO<SearchResponseDTO> scholar;
    private List<SearchResponseDTO> faculty;
    private CustomPaginationDTO<SearchResponseDTO> consulting;

    private Long totalCount;
}
