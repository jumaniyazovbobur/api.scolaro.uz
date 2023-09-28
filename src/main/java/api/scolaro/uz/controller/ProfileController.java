package api.scolaro.uz.controller;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.profile.*;

import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.service.ProfileService;
import io.swagger.annotations.Api;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile Api list", description = "Api list for profiles")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * FOR OWNER USER
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/update")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<ApiResponse<?>> update(@Valid @RequestBody ProfileUpdateDTO dto) {
        log.info("Update user ");
        return ResponseEntity.ok(profileService.update(dto));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getId(@PathVariable("id") String id) {
        log.info("Get user {}", id);
        return ResponseEntity.ok(profileService.getId(id));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter")
    @Operation(summary = "Filter profile api", description = "")
    public ResponseEntity<ApiResponse<PageImpl<ProfileResponseDTO>>> filter(@RequestBody ProfileFilterDTO userFilterDTO,
                                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                                            @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(profileService.filter(userFilterDTO, page - 1, size));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted profile api", description = "for admin")
    public ResponseEntity<ApiResponse<?>> deleted(@PathVariable("id") String id) {
        log.info("Deleted profile {}", id);
        return ResponseEntity.ok(profileService.deleted(id));
    }

    /**
     * FOR USER
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/update-phone")
    @Operation(summary = "Update  profile phone  api", description = "for user")
    public ResponseEntity<ApiResponse<?>> updatePhone(@RequestParam("phone") String phone ) {
        log.info("Update phone {}",phone);
        return ResponseEntity.ok(profileService.updatePhone(phone));
    }
    /**
     * FOR USER
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/phone-verification")
    @Operation(summary = "Update  profile phone verification api", description = "for user")
    public ResponseEntity<ApiResponse<?>> updatePhoneVerification(@RequestBody SmsDTO dto) {
        log.info("Update phone verification {}",dto.getPhone());
        return ResponseEntity.ok(profileService.verification(dto));
    }


    /**
     * FOR USER
     */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/update-password")
    @Operation(summary = "Update profile password api", description = "for user")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDTO dto) {
        log.info("Update password");
        return ResponseEntity.ok(profileService.updatePassword(dto));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/change-status/{id}")
    @Operation(summary = "Change profile status api", description = "for admin")
    public ResponseEntity<ApiResponse<?>> changeStatus(@PathVariable("id") String id,
                                                       @RequestParam("status") GeneralStatus status) {
        log.info("Change status {}", id);
        return ResponseEntity.ok(profileService.changeStatus(id, status));
    }


}
