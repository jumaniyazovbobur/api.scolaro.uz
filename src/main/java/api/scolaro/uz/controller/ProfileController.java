package api.scolaro.uz.controller;


import api.scolaro.uz.dto.profile.*;
import api.scolaro.uz.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile Api list", description = "Api list for profiles")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    /**
     * Current
     */
    @GetMapping("/current")
    @Operation(summary = "Get current profile detail", description = "")
    public ResponseEntity<ProfileDTO> getCurrentProfileDetail() {
        return ResponseEntity.ok(profileService.getCurrentProfileDetail());
    }
    @PutMapping("/current")
    @Operation(summary = "Update current profile detail", description = "")
    public ResponseEntity<?> updateDetail(@RequestBody UpdateProfileDetailDTO dto) {
        return ResponseEntity.ok(profileService.updateDetail(dto));
    }

    @PutMapping("/current/password")
    @Operation(summary = "Update current profile password", description = "")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO dto) {
        return ResponseEntity.ok(profileService.updatePassword(dto));
    }

    /**
     * Admin
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add profile", description = "Access for admin only")
    public ResponseEntity<ProfileDTO> addProfile(@RequestBody CreateProfileDTO dto) {
        return ResponseEntity.ok(profileService.addProfile(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get profile by id", description = "Access for admin only")
    public ResponseEntity<ProfileDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete profile", description = "Access for admin only")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update profile", description = "Access for admin only")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") String id, @RequestBody CreateProfileDTO dto) {
        return ResponseEntity.ok(profileService.updateProfile(id, dto));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Profile pagination", description = "Access for admin only")
    public ResponseEntity<Page<ProfileDTO>> filter(@RequestBody ProfileFilterRequestDTO dto,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "30") int size,
                                                   @RequestParam(value = "lang", defaultValue = "uz") AppLang lang) {
        return ResponseEntity.ok(profileService.filter(dto));
    }


}
