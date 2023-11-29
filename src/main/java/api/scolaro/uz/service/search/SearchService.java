package api.scolaro.uz.service.search;

import api.scolaro.uz.dto.search.res.SearchFilterResDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.repository.search.CustomPaginationForSearch;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public interface SearchService {
    CustomPaginationForSearch search(SearchFilterResDTO dto, int page, int size, AppLanguage language);
}
