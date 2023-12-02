package api.scolaro.uz.controller.application;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.*;
import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.enums.AppLanguage;
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

    @Operation(summary = "AppApplication list for Student. Web", description = "")
    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> applicationListForStudent_web(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "5") Integer size) {
        log.info("AppApplication list for student page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.getApplicationListForStudent_web(page, size));
    }

    @Operation(summary = "Filter Student AppApplicationConsulting list. Mobile", description = "Get Student ApplicationConsulting List for mobile. Mobile first page")
    @GetMapping("/mobile/student/consulting")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> studentApplicationConsultingList_mobile(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "20") Integer size) {
        log.info("Filtered appApplicationList list for student page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.getStudentApplicationConsultingList(page, size));
    }

    @Operation(summary = "Filter Student AppApplicationUniversity list by consulting id. Mobile", description = "Get Student ApplicationUniversity List for mobile. Mobile second page")
    @GetMapping("/mobile/student/{consultingId}/university")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> studentApplicationUniversityListByConsultingId_mobile(@PathVariable("consultingId") String consultingId,
                                                                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                                   @RequestParam(value = "size", defaultValue = "20") Integer size) {
        log.info("Filtered appApplicationList list for student page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.getStudentApplicationUniversityListByConsultingId(consultingId, page, size));
    }

    /**
     * CONSULTING
     */
    @Operation(summary = "Filter AppApplication for Consulting. Web", description = "Method user for filtering AppApplication for Consulting")
    @PostMapping("/consulting/filter")
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    public ResponseEntity<?> filterForConsulting(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                 @RequestBody AppApplicationFilterConsultingDTO dto) {
        log.info("Filtered appApplicationList for consulting page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.applicationFilterForConsulting(dto, page, size));
    }

    @Operation(summary = "Change appApplication  status as consulting", description = "")
    @PutMapping("/change-status/{applicationId}")
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    public ResponseEntity<?> changeStatus(@PathVariable("applicationId") String applicationId, @Valid @RequestBody AppApplicationChangeStatusDTO dto) {
        log.info("Change appApplication status as consulting. Application {}, toStatus {} ", applicationId, dto.getStatus());
        return ResponseEntity.ok(appApplicationService.changeStatus(applicationId, dto));
    }


    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Update application tariff ", description = "Updating consulting tariff id")
    @PostMapping("/consulting/{applicationId}/tariff")
    public ResponseEntity<?> updateTariffId(@RequestBody AppApplicationTariffIdUpdateDTO dto,
                                            @PathVariable("applicationId") String applicationId) {
        log.info("Update application {} tariff {}", applicationId, dto.getTariffId());
        return ResponseEntity.ok(appApplicationService.updateTariffId(dto, applicationId));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Update application consultingStep", description = "")
    @PostMapping("/consulting/{applicationId}/step")
    public ResponseEntity<?> updateStep(@RequestBody AppApplicationStepDTO dto,
                                        @PathVariable("applicationId") String applicationId) {
        log.info("Update application consultingStep. appId {}, stepId {}", applicationId, dto.getConsultingStepId());
        return ResponseEntity.ok(appApplicationService.updateStep(dto, applicationId));
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
    public ResponseEntity<ApiResponse<AppApplicationResponseDTO>> getById(@PathVariable String id, @RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get appApplication by id {}", id);
        return ResponseEntity.ok(appApplicationService.getById(id, language));
    }

}
