package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.university.*;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.UniversityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/university")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "University api list", description = "Api list for university")
public class UniversityController {

    private final UniversityService universityService;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    @Operation(summary = "Create university", description = "for admin")
    public ResponseEntity<ApiResponse<UniversityResponseDTO>> create(@Valid @RequestBody UniversityCreateDTO dto) {
        log.info("Create university");
        return ResponseEntity.ok(universityService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get university for admin", description = "")
    public ResponseEntity<ApiResponse<UniversityResponseDTO>> get(@PathVariable("id") Long id,
                                                                  @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
        log.info("Get university {}", id);
        return ResponseEntity.ok(universityService.getById(id, appLanguage));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update university", description = "")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable("id") Long id,
                                                 @Valid @RequestBody UniversityUpdateDTO dto) {
        log.info("Update university {}", id);
        return ResponseEntity.ok(universityService.update(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete university", description = "")
    public ResponseEntity<ApiResponse<?>> deleted(@PathVariable("id") Long id) {
        log.info("Delete university {}", id);
        return ResponseEntity.ok(universityService.deleted(id));
    }

    /**
     * FOR PUBLIC AUTH
     */

    @GetMapping("/{id}/detail")
    @Operation(summary = "Get university detail by id (Public)", description = "")
    public ResponseEntity<ApiResponse<UniversityResponseDTO>> getUniversityDetailById(@PathVariable("id") Long id,
                                                                                      @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
        log.info("Get university {}", id);
        return ResponseEntity.ok(universityService.getByIdDetail(id, appLanguage));
    }

    @PostMapping("/filter")
    @Operation(summary = "Get university list filter", description = "")
    public ResponseEntity<PageImpl<UniversityResponseFilterDTO>> filter(@RequestBody UniversityFilterDTO dto,
                                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", defaultValue = "30") int size,
                                                                        @RequestHeader(value = "Accept-Language",
                                                                                defaultValue = "uz") AppLanguage appLanguage) {
        log.info("Get filter university");
        return ResponseEntity.ok(universityService.filter(page - 1, size, dto, appLanguage));
    }

    @GetMapping("/top-university")
    @Operation(summary = "Get top university list filter", description = "")
    public ResponseEntity<ApiResponse<List<UniversityResponseFilterDTO>>> getTopUniversity(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage appLanguage) {
        log.info("Get top university");
        return ResponseEntity.ok(universityService.getTopUniversity(appLanguage));
    }

}
