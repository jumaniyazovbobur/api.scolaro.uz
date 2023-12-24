package api.scolaro.uz.controller.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.ConsultingProfileDTO;
import api.scolaro.uz.dto.consultingProfile.ConsultingProfileCreateDTO;
import api.scolaro.uz.dto.consultingProfile.ConsultingProfileUpdateDTO;
import api.scolaro.uz.dto.profile.UpdatePasswordDTO;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.service.consulting.ConsultingProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/consulting-profile")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting api list", description = "Api list for consulting")
public class ConsultingProfileController {

    @Autowired
    private ConsultingProfileService consultingProfileService;

    /**
     * FOR CONSULTING
     */
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/update-phone")
    @Operation(summary = "Update  consulting phone  api", description = "for consulting")
    public ResponseEntity<ApiResponse<String>> updatePhone(@RequestParam("phone") String phone) {
        log.info("Update phone {}", phone);
        return ResponseEntity.ok(consultingProfileService.updatePhone(phone));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/phone-verification")
    @Operation(summary = "Update  consulting phone verification api", description = "for consulting")
    public ResponseEntity<ApiResponse<String>> updatePhoneVerification(@RequestBody SmsDTO dto) {
        log.info("Update phone verification {}", dto.getPhone());
        return ResponseEntity.ok(consultingProfileService.updatePhoneVerification(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/update-password")
    @Operation(summary = "Update consulting password api", description = "for consulting")
    public ResponseEntity<ApiResponse<String>> updatePassword(@Valid @RequestBody UpdatePasswordDTO dto) {
        log.info("Update password");
        return ResponseEntity.ok(consultingProfileService.updatePassword(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/current")
    @Operation(summary = "Get current consulting detail", description = "for consulting")
    public ResponseEntity<ApiResponse<ConsultingProfileDTO>> currentConsultingProfile() {
        log.info("Get current consulting profile detail");
        return ResponseEntity.ok(consultingProfileService.getConsultingProfileDetail(EntityDetails.getCurrentUserId()));
    }


/*    @DeleteMapping("/delete-accTOount")
    @Operation(summary = "Delete your consulting api", description = "for consulting")
    public ResponseEntity<ApiResponse<?>> deletedOwn() {
        log.info("Delete your consulting");
        return ResponseEntity.ok(consultingService.deleteAccount());
    }*/


    /**
     * FOR CONSULTING_MANAGER
     */
    @PreAuthorize("hasRole('ROLE_CONSULTING_MANAGER')")
    @PostMapping("")
    @Operation(summary = "Create consulting profile", description = "for consulting manager")
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid ConsultingProfileCreateDTO dto) {
        log.info("Create consulting profile");
        return ResponseEntity.ok(consultingProfileService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING_MANAGER')")
    @PutMapping("/{id}")
    @Operation(summary = "Edit consulting profile", description = "for consulting manager")
    public ResponseEntity<ApiResponse<String>> update(@RequestBody @Valid ConsultingProfileUpdateDTO dto,
                                                      @PathVariable String id) {
        log.info("Update consulting profile");
        return ResponseEntity.ok(consultingProfileService.update(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING_MANAGER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete", description = "for consulting manager")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        log.info("Delete consulting profile");
        return ResponseEntity.ok(consultingProfileService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING_MANAGER')")
    @GetMapping("")
    @Operation(summary = "Delete", description = "for consulting manager")
    public ResponseEntity<ApiResponse<PageImpl<ConsultingProfileDTO>>> findAll(@RequestParam(name = "size", defaultValue = "50") int size,
                                                                               @RequestParam(name = "page", defaultValue = "1") int page) {
        log.info("FindAll consulting profile");
        if (page > 0) page--;
        String consultingId = Objects.requireNonNull(EntityDetails.getCurrentUserDetail()).getProfileConsultingId();
        return ResponseEntity.ok(consultingProfileService.findAll(consultingId, page, size));
    }

    @PutMapping("/update-status/{id}")
    @PreAuthorize("hasRole('ROLE_CONSULTING_MANAGER')")
    @Operation(summary = "Update status", description = "for consulting manager")
    public ResponseEntity<ApiResponse<String>> updateStatus(@PathVariable String id,
                                                            @RequestParam(name = "status") GeneralStatus status) {
        log.info("Update status {}", id);
        return ResponseEntity.ok(consultingProfileService.updateStatus(id,status));
    }
}
