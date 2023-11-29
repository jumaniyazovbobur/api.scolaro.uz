package api.scolaro.uz.dto;

import api.scolaro.uz.dto.search.SearchResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 28.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Getter
@Setter
public class CustomPaginationDTO<T> {
    private Long totalElement = 0L;
    private List<T> content;


    public CustomPaginationDTO(Long totalElement, List<T> content) {
        this.totalElement = totalElement;
        this.content = content;
    }
}
