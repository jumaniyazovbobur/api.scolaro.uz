package api.scolaro.uz.controller.search;

import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.repository.search.CustomPaginationForSearch;
import api.scolaro.uz.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@RestController
@RequestMapping("/api/v1/search")
@Slf4j
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("")
    public ResponseEntity<CustomPaginationForSearch> search(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "30") int size,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language
    ) {
        log.info("Search query={} page={}, size={}", query, page, size);
        return ResponseEntity.ok(searchService.search(query,page,size,language));
    }
}