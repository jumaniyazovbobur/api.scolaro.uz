package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.continent.ContinentDTO;
import api.scolaro.uz.dto.continent.ContinentRequestDTO;
import api.scolaro.uz.dto.continent.ContinentResponseDTO;
import api.scolaro.uz.dto.country.CountryPaginationDTO;
import api.scolaro.uz.dto.country.CountryRequestDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ContinentService;
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
@Api(tags = "Continent api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/continent")
public class ContinentController {

    private final ContinentService continentService;

    /**
     * PUBLIC
     */

    @ApiOperation(value = "Get continent List", notes = "Get Continent List with Language")
    @GetMapping("/public/get-all")
    public ResponseEntity<ApiResponse<List<ContinentResponseDTO>>> getCountryListByLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("get continent list");
        return ResponseEntity.ok().body(continentService.getList(language));
    }

    /**
     * ADMIN
     */

    @ApiOperation(value = "Continent Create", notes = "Continent Create admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ContinentDTO>> create(@Valid @RequestBody ContinentRequestDTO continentDTO) {
        log.info("Request for continent Create {}", continentDTO);
        return ResponseEntity.ok().body(continentService.continentCreate(continentDTO));
    }

    /**
     * FOR ADMIN
     */
    @ApiOperation(value = "Update Continent ", notes = "Update Continent for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContinentDTO>> update(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ContinentRequestDTO dto) {
        log.info("Request for Continent Update {}", dto);
        return ResponseEntity.ok().body(continentService.update(id, dto));
    }

    /**
     * FOR ADMIN
     */

    @ApiOperation(value = "Delete Continent", notes = "Continent Delete for admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Long id) {
        log.info("Request for Country delete {}", id);
        return ResponseEntity.ok().body(continentService.delete(id));
    }

}
