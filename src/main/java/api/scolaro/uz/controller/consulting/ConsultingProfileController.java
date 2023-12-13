package api.scolaro.uz.controller.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.consulting.ConsultingProfileDTO;
import api.scolaro.uz.dto.profile.UpdatePasswordDTO;
import api.scolaro.uz.service.consulting.ConsultingProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/current")
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


}
