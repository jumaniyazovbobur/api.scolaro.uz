package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.*;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.place.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Api(tags = "Country api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private final CountryService countryService;

    /**
     * PUBLIC
     */
    @ApiOperation(value = "Get country list for dropdown", notes = "Get all country list with language for dropdown used in registration dropdown")
    @GetMapping("/public/get-all")
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getCountryListByLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get country list for dropdown");
        return ResponseEntity.ok().body(countryService.getList(language));
    }

    @ApiOperation(value = "Get country list with university count")
    @GetMapping("/public/tree")
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getCountryListWithUniversityCount(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get country list with university count");
        return ResponseEntity.ok().body(countryService.getCountryListWithUniversityCount(language));
    }

    @ApiOperation(value = "Get country list with university count by continentId")
    @GetMapping("/public/continent/{continentId}")
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getCountryListWithUniversityCountByContinentId(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language, @PathVariable("continentId") Long continentId) {
        log.info("Get country list with university count by continentId");
        return ResponseEntity.ok().body(countryService.getCountryListWithUniversityCountByContinentId(continentId, language));
    }

    /**
     * ADMIN
     */

    @ApiOperation(value = "Country Create", notes = "Country Create admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> create(@RequestBody @Valid CountryRequestDTO countryDTO) {
        log.info("Request for Country Create {}", countryDTO);
        return ResponseEntity.ok().body(countryService.countryCreate(countryDTO));
    }

    @ApiOperation(value = "Update Country ", notes = "Method : Update Country for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> update(@PathVariable("id") Long id,
                                                                  @RequestBody @Valid CountryRequestDTO dto) {
        log.info("Request for Country Update {}", dto);
        return ResponseEntity.ok().body(countryService.update(id, dto));
    }

    @ApiOperation(value = "Delete country", notes = "Method : Country Delete for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Long id) {
        log.info("Request for Country delete {}", id);
        return ResponseEntity.ok().body(countryService.delete(id));
    }


    @ApiOperation(value = "Country pagination", notes = "Method : Country Pagination for admin", response = CountryPaginationDTO.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CountryResponseDTO>> pagination(@RequestBody CountryFilterDTO dto,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok().body(countryService.pagination(dto, page - 1, size));
    }

    @ApiOperation(value = "Country search by name", notes = "Method : Search country for admin", response = CountryResponseDTO.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/{query}")
    public ResponseEntity<List<CountryResponseDTO>> search(@PathVariable("query") String query, @RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok().body(countryService.search(query, appLanguage));
    }
}
