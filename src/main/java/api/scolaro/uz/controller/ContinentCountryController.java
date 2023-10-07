package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.continent.ContinentCountryRequestDTO;
import api.scolaro.uz.dto.continent.ContinentCountryResponseDTO;
import api.scolaro.uz.dto.continent.ContinentDTO;
import api.scolaro.uz.dto.continent.ContinentRequestDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ContinentCountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "Continent country api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/continent-country")
public class ContinentCountryController {

    private final ContinentCountryService continentCountryService;

    /**
     * PUBLIC
     */
    @ApiOperation(value = "Get country by continent List", notes = "Get Continent by country List with Language")
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

    @ApiOperation(value = "Continent country Create", notes = "Continent country Create admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ContinentCountryResponseDTO>> create(@Valid @RequestBody ContinentCountryRequestDTO requestDTO) {
        log.info("Continent country Create {}", requestDTO);
        return ResponseEntity.ok().body(continentCountryService.create(requestDTO));
    }

    /**
     * FOR ADMIN
     */
    @ApiOperation(value = "Update Continent country ", notes = "Update Continent country for admin")
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

    @ApiOperation(value = "Delete Continent country", notes = "Continent country Delete for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Long id) {
        log.info("Request for Continent country delete {}", id);
        return ResponseEntity.ok().body(continentCountryService.delete(id));
    }
}
