package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.continent.ContinentDTO;
import api.scolaro.uz.dto.continent.ContinentRequestDTO;
import api.scolaro.uz.dto.continent.ContinentResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ContinentService;
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
@Tag(name = "Continent api", description = "Api list for continent")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/continent")
public class ContinentController {

    private final ContinentService continentService;

    /**
     * PUBLIC
     */

    @Operation(summary = "get continent list ", description = "")
    @GetMapping("/public/get-all")
    public ResponseEntity<ApiResponse<List<ContinentResponseDTO>>> getCountryListByLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("get continent list");
        return ResponseEntity.ok().body(continentService.getList(language));
    }

    /**
     * ADMIN
     */

    @Operation(summary = "create continent", description = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ContinentDTO>> create(@Valid @RequestBody ContinentRequestDTO continentDTO) {
        log.info("Request for continent Create {}", continentDTO);
        return ResponseEntity.ok().body(continentService.continentCreate(continentDTO));
    }


    @Operation(summary = "Get continent by id ", description = "")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContinentDTO>> getById(@PathVariable("id") Long id) {
        log.info("Get continent by id");
        return ResponseEntity.ok().body(continentService.getById(id));
    }

    /**
     * FOR ADMIN
     */
    @Operation(summary = "update continent", description = "")
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

    @Operation(summary = "delete continent", description = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Long id) {
        log.info("Request for Country delete {}", id);
        return ResponseEntity.ok().body(continentService.delete(id));
    }

}
