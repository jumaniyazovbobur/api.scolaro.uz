package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.CountryRequest;
import api.scolaro.uz.dto.country.CountryResponse;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.place.CountryService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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
    private final CountryService service;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create Country", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CountryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    @Operation(summary = "Update Country", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody CountryRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all country for admin", description = "")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> allForAdmin() {
        return ResponseEntity.ok(service.allForAdmin());
    }

    @GetMapping("/language")
    @Operation(summary = "Get all country by language", description = "")
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> allByLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getAllByLanguage(language));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get id Country ", description = "")
    public ResponseEntity<ApiResponse<CountryResponse>> getId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Country", description = "for admin")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
