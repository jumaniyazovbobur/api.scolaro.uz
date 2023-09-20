package api.scolaro.uz.controller;



import api.scolaro.uz.dto.ResetPasswordConfirmDTO;
import api.scolaro.uz.dto.ResetPasswordRequestDTO;
import api.scolaro.uz.service.AuthService;
import api.scolaro.uz.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth api list", description = "Api list for authorization and authentication")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProfileService profileService;

    @PostMapping("/login")
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
    }
    // TODO registration

}
