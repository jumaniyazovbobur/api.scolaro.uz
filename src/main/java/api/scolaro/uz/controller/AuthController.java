package api.scolaro.uz.controller;


import api.scolaro.uz.dto.auth.AuthRequestDTO;
import api.scolaro.uz.dto.client.ClientRequestDTO;
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
   /* @PostMapping("/login")
    @Operation(summary = "Login api", description = "")
    public ResponseEntity<ProfileResponseDTO> goToLogin(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping("/pswd/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO dto) {
        authService.resetPasswordRequest(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/pswd/reset/confirm")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordConfirmDTO dto) {
        authService.resetPasswordConfirm(dto);
        return ResponseEntity.ok().build();
    }*/


    /**
     * Client
     */
    @Operation(summary = "Client registration", description = "Method client for  Registration")
    @PostMapping("/client/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid ClientRequestDTO dto) {
        log.info("Registration {}", dto);
        return ResponseEntity.ok(profileService.registration(dto));
    }

    @Operation(summary = "Profile login", description = "Method profile for  Login")
    @PostMapping("/profile/login")
    public ResponseEntity<?> profileLogin(@RequestBody @Valid AuthRequestDTO dto) {
        return null;
    }

//    consulting

    @Operation(summary = "Consulting login", description = "Method consulting for  Login")
    @PostMapping("/consulting/login")
    public ResponseEntity<?> consultingLogin(@RequestBody @Valid AuthRequestDTO dto) {
        return null;
    }
}
