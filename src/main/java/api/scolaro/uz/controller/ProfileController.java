package api.scolaro.uz.controller;


import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.profile.*;

import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.service.ProfileService;
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
    @Operation(summary = "Update api", description = "for student")
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody ProfileUpdateDTO dto) {
        log.info("Update user ");
        String currentUserId = EntityDetails.getCurrentUserId();
        return ResponseEntity.ok(profileService.update(dto, currentUserId));
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/update-lang")
    @Operation(summary = "Update lang", description = "for student")
    public ResponseEntity<ApiResponse<String>> updateLang(@RequestParam AppLanguage lang) {
        log.info("Update user lang +{} ", lang);
        String currentUserId = EntityDetails.getCurrentUserId();
        return ResponseEntity.ok(profileService.updateLang(lang, currentUserId));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getId(@PathVariable("id") String id) {
        log.info("Get user {}", id);
        return ResponseEntity.ok(profileService.getId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/change-role/{id}")
    @Operation(summary = "Update profile role", description = "for admin")
    public ResponseEntity<ApiResponse<String>> changeProfileRole(@PathVariable("id") String id,
                                                                 @RequestBody @Valid ChangeProfileRoleReqDTO dto) {
        log.info("Change profile role profileId={},roles={}", id, dto.getRoles());
        return ResponseEntity.ok(profileService.changeRole(id, dto));
    }


    /**
     * FOR STUDENT
     */
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
    @GetMapping("/current")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<ApiResponse<CurrentProfileDTO>> getCurrentProfile() {
        log.info("Get current user {}", EntityDetails.getCurrentUserId());
        return ResponseEntity.ok(profileService.getCurrentProfile());
    }


    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/filter")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN')")
    @PutMapping("/update-phone")
    @Operation(summary = "Update  profile phone  api", description = "for user")
    public ResponseEntity<ApiResponse<?>> updatePhone(@RequestParam("phone") String phone) {
        log.info("Update phone {}", phone);
        return ResponseEntity.ok(profileService.updatePhone(phone));
    }

    @DeleteMapping("/delete-account")
    @Operation(summary = "Delete your profile api", description = "for user")
    public ResponseEntity<ApiResponse<?>> deletedOwn() {
        log.info("Delete your profile");
        return ResponseEntity.ok(profileService.deleteAccount());
    }

    /**
     * FOR USER
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN')")
    @PutMapping("/phone-verification")
    @Operation(summary = "Update  profile phone verification api", description = "for user")
    public ResponseEntity<ApiResponse<?>> updatePhoneVerification(@RequestBody SmsDTO dto) {
        log.info("Update phone verification {}", dto.getPhone());
        return ResponseEntity.ok(profileService.verification(dto));
    }


    /**
     * FOR USER
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN')")
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
