package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.continent.ContinentCountryRequestDTO;
import api.scolaro.uz.dto.continent.ContinentCountryResponseDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.place.ContinentCountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Continent country api", description = "Api list for continent country")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/continent-country")
public class ContinentCountryController {

    private final ContinentCountryService continentCountryService;

    /**
     * PUBLIC
     */
    @Operation(summary = "get continent country list ", description = "")
    @GetMapping("/public/get-all/{id}")
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getCountryListByLanguage(@PathVariable("id") Long continentId,
                                                                                          @RequestHeader(value = "Accept-Language",
                                                                                                  defaultValue = "uz") AppLanguage language) {
        log.info("get continent country list");
        return ResponseEntity.ok().body(continentCountryService.getList(continentId,language));
    }

    /**
     * ADMIN
     */

    @Operation(summary = "get continent country create ", description = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ContinentCountryResponseDTO>> create(@Valid @RequestBody ContinentCountryRequestDTO requestDTO) {
        log.info("Continent country Create {}", requestDTO);
        return ResponseEntity.ok().body(continentCountryService.create(requestDTO));
    }

    /**
     * FOR ADMIN
     */
    @Operation(summary = "get continent country update ", description = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContinentCountryResponseDTO>> update(@PathVariable("id") Long id,
                                                            @Valid @RequestBody ContinentCountryRequestDTO dto) {
        log.info("Request for Continent country Update {}", dto);
        return ResponseEntity.ok().body(continentCountryService.update(id, dto));
    }

    /**
     * FOR ADMIN
     */

    @Operation(summary = "get continent country delete ", description = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Long id) {
        log.info("Request for Continent country delete {}", id);
        return ResponseEntity.ok().body(continentCountryService.delete(id));
    }
}
