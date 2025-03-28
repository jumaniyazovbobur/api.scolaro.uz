package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.CountryResponse;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.program.ProgramFilterDTO;
import api.scolaro.uz.dto.program.ProgramResponseDTO;
import api.scolaro.uz.dto.university.UniversityFilterDTO;
import api.scolaro.uz.dto.university.UniversityResponseFilterDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ProgramService;
import api.scolaro.uz.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/program")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Program api")
public class ProgramController {
    private final ProgramService service;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody ProgramCreateDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    @Operation(summary = "Update program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody ProgramCreateDTO request) {
        return ResponseEntity.ok(service.update(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/publish/{id}")
    @Operation(summary = "Publish program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> publishBlock(@PathVariable("id") Long id,
                                                            @RequestParam(defaultValue = "false") Boolean published) {
        return ResponseEntity.ok(service.publishBlock(id, published));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get id program ", description = "")
    public ResponseEntity<ApiResponse<ProgramResponseDTO>> getId(@PathVariable("id") Long id,
                                                                 @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getById(id, language));
    }

    @GetMapping("/filter")
    @Operation(summary = "Get program list filter", description = "")
    public ResponseEntity<PageImpl<ProgramResponseDTO>> filter(@RequestBody ProgramFilterDTO dto,
                                                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", defaultValue = "30") int size,
                                                                        @RequestHeader(value = "Accept-Language",
                                                                                defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(service.filter(PaginationUtil.page(page), size, dto, appLanguage));
    }
    // filter user-lar + pagination
    // filter admin  + pagination

}
