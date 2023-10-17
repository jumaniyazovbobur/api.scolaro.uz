package api.scolaro.uz.controller;
// PROJECT NAME -> api.dachatop
// TIME -> 22:13
// MONTH -> 07
// DAY -> 30


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.*;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.place.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @ApiOperation(value = "Get Country List", notes = "Get Country List with Language")
    @GetMapping("/public/get-all")
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getCountryListByLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("get country");
        return ResponseEntity.ok().body(countryService.getList(language));
    }

    /**
     * ADMIN
     */

    @ApiOperation(value = "Country Create", notes = "Country Create admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> create(@RequestBody CountryRequestDTO countryDTO) {
        log.info("Request for Country Create {}", countryDTO);
        return ResponseEntity.ok().body(countryService.countryCreate(countryDTO));
    }

    /**
     * FOR ADMIN
     */
    @ApiOperation(value = "Update Country ", notes = "Method : Update Country for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> update(@PathVariable("id") Long id,
                                                                  @RequestBody @Valid CountryRequestDTO dto) {
        log.info("Request for Country Update {}", dto);
        return ResponseEntity.ok().body(countryService.update(id, dto));
    }

    /**
     * FOR ADMIN
     */

    @ApiOperation(value = "Delete Country", notes = "Method : Country Delete for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Long id) {
        log.info("Request for Country delete {}", id);
        return ResponseEntity.ok().body(countryService.delete(id));
    }

    /**
     * FOR ADMIN
     */
    @ApiOperation(value = "Country Pagination", notes = "Method : Country Pagination for admin", response = CountryPaginationDTO.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<CountryPaginationDTO> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok().body(countryService.pagination(page-1, size));
    }
}
