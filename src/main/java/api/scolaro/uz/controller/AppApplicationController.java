package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.*;
import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.service.AppApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/app-application")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "App Application api list", description = "Api list for App Application")
public class AppApplicationController {

    private final AppApplicationService appApplicationService;

    /**
     * STUDENT
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @Operation(summary = "AppApplication create", description = "Method user for  AppApplication")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AppApplicationRequestDTO dto) {
        log.info("appApplication create {}", dto);
        return ResponseEntity.ok(appApplicationService.create(dto));
    }

    @Operation(summary = "Filter AppApplication for Student", description = "Method user for filtering AppApplication for Student")
    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> filterForStudent(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "5") Integer size) {
        log.info("Filtered appApplicationList for student page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.filterForStudent(page, size));
    }

    /**
     * CONSULTING
     */
    @Operation(summary = "Filter AppApplication for Consulting", description = "Method user for filtering AppApplication for Consulting")
    @PostMapping("/consulting/filter")
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    public ResponseEntity<?> filterForConsulting(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                 @RequestBody AppApplicationFilterConsultingDTO dto) {
        log.info("Filtered appApplicationList for consulting page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.filterForConsulting(dto, page, size));
    }

    @Operation(summary = "Change status  AppApplication ", description = "Method user for change status AppApplication for Consulting")
    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    public ResponseEntity<?> changeStatus(@PathVariable String id, @Valid @RequestBody AppApplicationChangeStatusDTO dto) {
        log.info("Change status appApplication for consulting ");
        return ResponseEntity.ok(appApplicationService.changeStatus(id, dto));
    }

    /**
     * ADMIN
     */
    @Operation(summary = "Filter AppApplication", description = "Method user for filtering AppApplication")
    @PostMapping("/adm/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                                    @RequestBody AppApplicationFilterDTO dto) {
        log.info("Admin filtered appApplicationList  page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.filterForAdmin(dto, page, size));
    }

    /**
     * ANY
     */
    @Operation(summary = "Get AppApplication by id", description = "Method user for get AppApplication by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppApplicationResponseDTO>> getById(@PathVariable String id) {
        log.info("Get appApplication by id {}", id);
        return ResponseEntity.ok(appApplicationService.getById(id));
    }

}
