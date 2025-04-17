package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.institute.InstituteRequestDTO;
import api.scolaro.uz.dto.institute.InstituteResponseAdminDTO;
import api.scolaro.uz.dto.university.*;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.service.institute.InstituteService;
import api.scolaro.uz.service.UniversityService;
import api.scolaro.uz.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/api/v1/institute")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Institute api list", description = "Api list for institute")
public class InstituteController {

    private final InstituteService service;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    @Operation(summary = "Create institute", description = "for admin")
    public ResponseEntity<ApiResponse<InstituteResponseAdminDTO>> create(@Valid @RequestBody InstituteRequestDTO dto) {
        log.info("Create institute");
        return ResponseEntity.ok(service.create(dto));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/{id}")
//    @Operation(summary = "Get institute for admin", description = "")
//    public ResponseEntity<ApiResponse<UniversityResponseDTO>> get(@PathVariable("id") Long id,
//                                                                  @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
//        log.info("Get institute {}", id);
//        return ResponseEntity.ok(universityService.getById(id, appLanguage));
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/{id}")
//    @Operation(summary = "Update institute", description = "")
//    public ResponseEntity<ApiResponse<?>> update(@PathVariable("id") Long id,
//                                                 @Valid @RequestBody UniversityUpdateDTO dto,
//                                                 @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
//        log.info("Update institute {}", id);
//        return ResponseEntity.ok(universityService.update(id, dto, appLanguage));
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Delete institute", description = "")
//    public ResponseEntity<ApiResponse<?>> deleted(@PathVariable("id") Long id) {
//        log.info("Delete institute {}", id);
//        return ResponseEntity.ok(universityService.deleted(id));
//    }
//
//    /**
//     * CONSULTING
//     */
//    @PreAuthorize("hasRole('ROLE_CONSULTING')")
//    @GetMapping("/consulting/application")
//    @Operation(summary = "Get application institute list for consulting. Consulting mobile first page", description = "")
//    public ResponseEntity<ApiResponse<Page<UniversityResponseDTO>>> getApplicationUniversityListForConsulting(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                                                                              @RequestParam(value = "size", defaultValue = "30") int size) {
//        log.info("Get application institute list for consulting. Consulting mobile first page");
//        return ResponseEntity.ok(universityService.getApplicationUniversityListForConsulting_mobile(PaginationUtil.page(page), size));
//    }
//
//    /**
//     * FOR PUBLIC AUTH
//     */
//
//    @GetMapping("/{id}/detail")
//    @Operation(summary = "Get institute detail by id (Public). Used in institute page", description = "")
//    public ResponseEntity<ApiResponse<UniversityResponseDTO>> getUniversityDetailById(@PathVariable("id") Long id,
//                                                                                      @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage appLanguage) {
//        log.info("Get institute {}", id);
//        return ResponseEntity.ok(universityService.getByIdDetail(id, appLanguage));
//    }
//
//    @PostMapping("/filter")
//    @Operation(summary = "Get institute list filter", description = "")
//    public ResponseEntity<PageImpl<UniversityResponseFilterDTO>> filter(@RequestBody UniversityFilterDTO dto,
//                                                                        @RequestParam(value = "page", defaultValue = "1") int page,
//                                                                        @RequestParam(value = "size", defaultValue = "30") int size,
//                                                                        @RequestHeader(value = "Accept-Language",
//                                                                                defaultValue = "uz") AppLanguage appLanguage) {
//        log.info("Get filter institute");
//        return ResponseEntity.ok(universityService.filter(PaginationUtil.page(page), size, dto, appLanguage));
//    }
//
//    @GetMapping("/top-institute")
//    @Operation(summary = "Get top institute list filter", description = "")
//    public ResponseEntity<ApiResponse<List<UniversityResponseFilterDTO>>> getTopUniversity(@RequestHeader(value = "Accept-Language",
//            defaultValue = "uz") AppLanguage appLanguage) {
//        log.info("Get top institute");
//        return ResponseEntity.ok(universityService.getTopUniversity(appLanguage));
//    }
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/change-status/{id}")
//    @Operation(summary = "Change institute status api", description = "for admin")
//    public ResponseEntity<ApiResponse<String>> changeStatus(@PathVariable("id") Long id,
//                                                            @RequestParam("status") GeneralStatus status) {
//        log.info("Change status {}", id);
//        return ResponseEntity.ok(universityService.changeStatus(id, status));
//    }
}
