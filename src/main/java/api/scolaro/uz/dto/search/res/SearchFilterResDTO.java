package api.scolaro.uz.dto.search.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 'Mukhtarov Sarvarbek' on 28.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilterResDTO {
    String query;
    Long countryId;
    Long continentId;
    String facultyId;
    String searchType; // SCHOLARSHIP, CONSULTING, UNIVERSITY
    //    String scholarShipId;
    //    Long universityId;
//    String consultingId;
}
