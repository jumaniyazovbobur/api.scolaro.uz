package api.scolaro.uz.controller.consulting;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.*;
import api.scolaro.uz.dto.profile.UpdatePasswordDTO;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.service.consulting.ConsultingService;
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
@RequestMapping("/api/v1/consulting")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting api list", description = "Api list for consulting")
public class ConsultingController {

    private final ConsultingService consultingService;

    /**
     * CONSULTING
     */
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/detail")
    @Operation(summary = "Consulting update own detail", description = "")
    public ResponseEntity<ApiResponse<ConsultingResponseDTO>> updateDetail(@Valid @RequestBody ConsultingUpdateDTO dto) {
        log.info("Update consulting {}", dto.getName());
        return ResponseEntity.ok(consultingService.updateDetail(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/current")
    @Operation(summary = "Get current consulting by id api", description = "for consulting")
    public ResponseEntity<ApiResponse<ConsultingDTO>> current() {
        log.info("Get consulting ");
        return ResponseEntity.ok(consultingService.getCurrentConsultingDetail());

    }

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    @Operation(summary = "Create consulting", description = "for admin")
    public ResponseEntity<ApiResponse<ConsultingResponseDTO>> create(@RequestBody @Valid ConsultingCreateDTO dto) {
        log.info("Create consulting {}", dto.getName());
        return ResponseEntity.ok(consultingService.create(dto));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "for admin")
    public ResponseEntity<ApiResponse<ConsultingResponseDTO>> getId(@PathVariable("id") String id) {
        log.info("Get consulting {}", id);
        return ResponseEntity.ok(consultingService.getId(id));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/filter")
    @Operation(summary = "Filter api", description = "for admin")
    public ResponseEntity<PageImpl<ConsultingResponseDTO>> filter(@RequestBody ConsultingFilterDTO consultingFilterDTO,
                                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(consultingService.filter(consultingFilterDTO, page - 1, size));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted consulting api", description = "for admin")
    public ResponseEntity<ApiResponse<String>> deleted(@PathVariable("id") String id) {
        log.info("Deleted consulting {}", id);
        return ResponseEntity.ok(consultingService.deleted(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update consulting detail as admin", description = "")
    public ResponseEntity<ApiResponse<ConsultingResponseDTO>> updateConsulting(@PathVariable("id") String id,
                                                           @Valid @RequestBody ConsultingUpdateDTO dto) {
        log.info("Update consulting detail {}", dto.getName());
        return ResponseEntity.ok(consultingService.updateConsulting(id, dto));
    }


    /**
     * FOR CONSULTING
     */
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/update-phone")
    @Operation(summary = "Update  consulting phone  api", description = "for consulting")
    public ResponseEntity<ApiResponse<String>> updatePhone(@RequestParam("phone") String phone) {
        log.info("Update phone {}", phone);
        return ResponseEntity.ok(consultingService.updatePhone(phone));
    }

    /**
     * FOR CONSULTING
     */
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/phone-verification")
    @Operation(summary = "Update  consulting phone verification api", description = "for consulting")
    public ResponseEntity<ApiResponse<String>> updatePhoneVerification(@RequestBody SmsDTO dto) {
        log.info("Update phone verification {}", dto.getPhone());
        return ResponseEntity.ok(consultingService.verification(dto));
    }


    /**
     * FOR CONSULTING
     */
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/update-password")
    @Operation(summary = "Update consulting password api", description = "for consulting")
    public ResponseEntity<ApiResponse<String>> updatePassword(@Valid @RequestBody UpdatePasswordDTO dto) {
        log.info("Update password");
        return ResponseEntity.ok(consultingService.updatePassword(dto));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/change-status/{id}")
    @Operation(summary = "Change consulting status api", description = "for admin")
    public ResponseEntity<ApiResponse<String>> changeStatus(@PathVariable("id") String id,
                                                       @RequestParam("status") GeneralStatus status) {
        log.info("Change status {}", id);
        return ResponseEntity.ok(consultingService.changeStatus(id, status));
    }

}
