package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.*;
import api.scolaro.uz.dto.countryFlag.CountryRequest;
import api.scolaro.uz.dto.countryFlag.CountryResponse;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.place.CountryService;
import api.scolaro.uz.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
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
    private final CountryService service;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create Country flag", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CountryRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    @Operation(summary = "Update Country flag", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody CountryRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping()
    @Operation(summary = "Get all Country flag", description = "")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/language")
    @Operation(summary = "Get all Country flag Language", description = "")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> getAllLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getAllLanguage(language));
    }

    @GetMapping("/language/{id}")
    @Operation(summary = "Get id Country flag Language", description = "")
    public ResponseEntity<ApiResponse<CountryResponse>> getIdLanguage(@PathVariable("id") Long id,
                                                                      @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getIdLanguage(id, language));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get id Country flag ", description = "")
    public ResponseEntity<ApiResponse<CountryResponse>> getId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Country flag", description = "for admin")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
