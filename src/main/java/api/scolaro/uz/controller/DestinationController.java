package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.destination.DestinationLanguageResponse;
import api.scolaro.uz.dto.destination.DestinationRequest;
import api.scolaro.uz.dto.destination.DestinationResponse;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.place.DestinationService;
import api.scolaro.uz.util.PaginationUtil;
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
@Api(tags = "Destination api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/destination")
public class DestinationController {
    private final DestinationService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create Destination", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update Destination", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable("id") Long id,
                                                      @Valid @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(service.update(id,request));
    }


    @GetMapping("/language")
    @Operation(summary = "Get all destination by language", description = "")
    public ResponseEntity<ApiResponse<List<DestinationLanguageResponse>>> allByLanguage(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getAllByLanguage(language));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get id Destination ", description = "")
    public ResponseEntity<ApiResponse<DestinationResponse>> getId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Destination", description = "for admin")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter")
    @Operation(summary = "Filter Destination api", description = "")
    public ResponseEntity<ApiResponse<List<DestinationResponse>>> filter(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(service.filter(PaginationUtil.page(page), size));
    }

}
