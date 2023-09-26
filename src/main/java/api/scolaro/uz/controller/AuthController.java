package api.scolaro.uz.controller;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.auth.AuthRequestProfileDTO;
import api.scolaro.uz.dto.client.AuthRequestDTO;
import api.scolaro.uz.service.AuthService;
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

    //TODO -> reset password,(2ta api) for profile

    /**
     * Consulting
     */
    @Operation(summary = "Consulting login", description = "Method consulting for  Login")
    @PostMapping("/consulting/login")
    public ResponseEntity<?> consultingLogin(@RequestBody @Valid AuthRequestProfileDTO dto) {
        log.info("Client login {}", dto);
        return ResponseEntity.ok(authService.consultingLogin(dto));
    }

    //TODO rest password for consulting. 2 api.

}
