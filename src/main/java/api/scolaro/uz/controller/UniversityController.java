package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.university.UniversityCreateDTO;
import api.scolaro.uz.dto.university.UniversityFilterDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.dto.university.UniversityUpdateDTO;
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
    @PostMapping("/")
    @Operation(summary = "Create consulting", description = "for admin")
    public ResponseEntity<ApiResponse<?>> create(@Valid UniversityCreateDTO dto) {
        log.info("Create university");
        return ResponseEntity.ok(universityService.create(dto));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("get/{id}")
    @Operation(summary = "Create consulting", description = "")
    public ResponseEntity<ApiResponse<?>> get(@PathVariable("id") Long id) {
        log.info("Get university {}", id);
        return ResponseEntity.ok(universityService.getById(id));
    }

    /**
     * FOR PUBLIC AUTH
     */

    @GetMapping("/filter")
    @Operation(summary = "Get university list filter", description = "")
    public ResponseEntity<PageImpl<UniversityResponseDTO>> filter(@RequestBody UniversityFilterDTO dto,
                                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "30") int size) {
        log.info("Get filter university");
        return ResponseEntity.ok(universityService.filter(page - 1, size, dto));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("update/{id}")
    @Operation(summary = "Update university", description = "")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable("id") Long id,
                                                 @Valid @RequestBody UniversityUpdateDTO dto) {
        log.info("Update university {}", id);
        return ResponseEntity.ok(universityService.update(id, dto));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete university", description = "")
    public ResponseEntity<ApiResponse<?>> deleted(@PathVariable("id") Long id) {
        log.info("Delete university {}", id);
        return ResponseEntity.ok(universityService.deleted(id));
    }


}
