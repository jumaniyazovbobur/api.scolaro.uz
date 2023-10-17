package api.scolaro.uz.controller;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.auth.*;
import api.scolaro.uz.dto.auth.AuthRequestDTO;
import api.scolaro.uz.service.AuthService;
import api.scolaro.uz.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth api list", description = "Api list for authorization and authentication")
public class AuthController {

    private final AuthService authService;
    private final ProfileService profileService;

    /**
     * Client
     */
    @Operation(summary = "profile registration", description = "Method user for  Registration")
    @PostMapping("/profile/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid AuthRequestDTO dto) {
        log.info("Registration {}", dto);
        return ResponseEntity.ok(authService.registration(dto));
    }

    @Operation(summary = "User registration verification", description = "Method used for user registration verification")
    @PostMapping("/profile/registration/verification")
    public ResponseEntity<ApiResponse<?>> registrationVerification(@RequestBody @Valid SmsDTO dto) {
        log.info("Registration verification {}", dto);
        return ResponseEntity.ok(authService.profileRegistrationVerification(dto));
    }

    @Operation(summary = "Profile login", description = "Method profile for  Login")
    @PostMapping("/profile/login")
    public ResponseEntity<?> profileLogin(@RequestBody @Valid AuthRequestProfileDTO dto) {
        log.info("Client login {}", dto);
        return ResponseEntity.ok(authService.profileLogin(dto));
    }

    @PostMapping("/profile/reset-password")
    @Operation(summary = "Profile reset password", description = "Method profile for  reset password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid AuthResetProfileDTO dto) {
        log.info("Profile reset password {}", dto);
        return ResponseEntity.ok(authService.resetPasswordRequest(dto));
    }

    @PutMapping("/profile/reset/confirm")
    @Operation(summary = "Profile reset password confirm", description = "Method profile for  reset password confirm")
    public ResponseEntity<?> resetPasswordConfirm(@Valid @RequestBody ResetPasswordConfirmDTO dto) {
        log.info("Client Reset password confirm {}", dto);
        return ResponseEntity.ok(authService.resetPasswordConfirm(dto));
    }

    @GetMapping("")
    @Operation(summary = "Get by Nick Name api", description = "")
    public ResponseEntity<?> getProfileByNickName(@RequestBody AuthNickNameDTO dto) {
        log.info("Get user by nickName {}", dto);
        return ResponseEntity.ok(profileService.getProfileByNickName(dto));
    }

    /**
     * Consulting
     */
    @Operation(summary = "Consulting login", description = "Method consulting for  Login")
    @PostMapping("/consulting/login")
    public ResponseEntity<?> consultingLogin(@RequestBody @Valid AuthRequestProfileDTO dto) {
        log.info("Client login {}", dto);
        return ResponseEntity.ok(authService.consultingLogin(dto));
    }


    @PostMapping("/consulting/reset-password")
    @Operation(summary = "Consulting reset password", description = "Method consulting for  reset password")
    public ResponseEntity<?> resetPasswordConsulting(@RequestBody @Valid AuthResetProfileDTO dto) {
        log.info("Consulting reset password {}", dto);
        return ResponseEntity.ok(authService.resetPasswordConsultingRequest(dto));
    }

    @PutMapping("/consulting/reset/confirm")
    @Operation(summary = "Consulting reset password confirm", description = "Method consulting for  reset password confirm")
    public ResponseEntity<?> resetPasswordConfirmConsulting(@Valid @RequestBody ResetPasswordConfirmDTO dto) {
        log.info("Consulting Reset password confirm {}", dto);
        return ResponseEntity.ok(authService.resetPasswordConsultingConfirm(dto));
    }

}
