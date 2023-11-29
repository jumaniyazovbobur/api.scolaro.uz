package api.scolaro.uz.service.impl.search;

import api.scolaro.uz.dto.search.res.SearchFilterResDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.repository.search.CustomPaginationForSearch;
import api.scolaro.uz.repository.search.SearchRepository;
import api.scolaro.uz.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


/**
 * @author 'Mukhtarov Sarvarbek' on 27.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Override
    public CustomPaginationForSearch search(SearchFilterResDTO dto, int page, int size, AppLanguage language) {
        return searchRepository.search(dto, language.name(), page, size);
    }
}
