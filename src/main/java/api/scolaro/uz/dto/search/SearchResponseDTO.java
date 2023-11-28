package api.scolaro.uz.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDTO {
    private String id;
    private String name;
    private String type; // UNIVERSITY,CONSULTING,COUNTRY,SCHOLAR
}
