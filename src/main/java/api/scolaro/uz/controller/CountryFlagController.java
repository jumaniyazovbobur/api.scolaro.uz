package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.countryFlag.CountryFlagRequest;
import api.scolaro.uz.dto.countryFlag.CountryFlagResponse;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.countryFlag.CountryFlagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country-flag")
@RequiredArgsConstructor
@Tag(name = "Country flag Api list", description = "Api list for country flag")
public class CountryFlagController {

    private final CountryFlagService service;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create Country flag", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CountryFlagRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    @Operation(summary = "Update Country flag", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody CountryFlagRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping()
    @Operation(summary = "Get all Country flag", description = "")
    public ResponseEntity<ApiResponse<List<CountryFlagResponse>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/language")
    @Operation(summary = "Get all Country flag Language", description = "")
    public ResponseEntity<ApiResponse<List<CountryFlagResponse>>> getAllLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getAllLanguage(language));
    }

    @GetMapping("/language/{id}")
    @Operation(summary = "Get id Country flag Language", description = "")
    public ResponseEntity<ApiResponse<CountryFlagResponse>> getIdLanguage(@PathVariable("id") String id,
                                                                          @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getIdLanguage(id, language));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get id Country flag ", description = "")
    public ResponseEntity<ApiResponse<CountryFlagResponse>> getId(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Country flag", description = "for admin")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.delete(id));
    }


}
