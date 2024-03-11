package api.scolaro.uz.service.junit5;

import api.scolaro.uz.AbstractTestContainers;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.auth.*;
import api.scolaro.uz.entity.sms.SmsHistoryEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.repository.sms.SmsHistoryRepository;
import api.scolaro.uz.service.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Admin on 23.02.2024
 * @project api.scolaro.uz
 * @package api.scolaro.uz.service
 * @contact @sarvargo
 */
@SpringBootTest
@Testcontainers
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthServiceTest extends AbstractTestContainers {

    @Autowired
    private AuthService authService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    private AuthRequestDTO authRequestDTO;
    private AuthRequestProfileDTO authRequest;
    private AppLanguage appLanguage;
    private String newPassword;

    @BeforeEach
    void setUp() {
        authRequestDTO = AuthRequestDTO
                .builder()
                .name("Sarvarbek")
                .surname("Mukhtarov")
                .phoneNumber("998972322903")
                .password("123456")
                .nickName("sarvargo")
                .countryId(1L)
                .address("Tashkent")
                .build();
        // set language
        appLanguage = AppLanguage.uz;
        //
        authRequest = AuthRequestProfileDTO
                .builder()
                .phone(authRequestDTO.getPhoneNumber())
                .password(authRequestDTO.getPassword())
                .build();
        newPassword = "change123456";
    }

    @Test
    @Order(1)
    @DisplayName("Registration Success Case")
    void registration_success_case() {
        ApiResponse<String> registrationResponse = authService
                .registration(authRequestDTO);
        assertFalse(registrationResponse.getIsError());
    }

    @Test
    @Order(2)
    @DisplayName("Registration Failed Case")
    void registration_failed_case() {
        ApiResponse<String> registrationResponse = authService
                .registration(authRequestDTO);
        assertTrue(registrationResponse.getIsError());
    }

    @Test
    @Order(3)
    @DisplayName("Profile Registration Verification Fail Case")
    void profile_registration_verification_fail_case() {
        String phoneNumber = authRequestDTO.getPhoneNumber();

        SmsDTO requestDTO = SmsDTO.builder()
                .phone(phoneNumber)
                .code("123")
                .build();
        ApiResponse<AuthResponseDTO> verificationResponse = authService.profileRegistrationVerification(requestDTO, appLanguage);
        assertTrue(verificationResponse.getIsError());
    }

    @Test
    @Order(4)
    @DisplayName("Profile Registration Verification Success Case")
    void profile_registration_verification_success_case() {
        String phoneNumber = authRequestDTO.getPhoneNumber();
        Optional<SmsHistoryEntity> smsHistory = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phoneNumber);
        assertTrue(smsHistory.isPresent());

        SmsDTO requestDTO = SmsDTO.builder()
                .phone(phoneNumber)
                .code(smsHistory.get().getSmsCode())
                .build();
        ApiResponse<AuthResponseDTO> verificationResponse = authService.profileRegistrationVerification(requestDTO, appLanguage);
        assertFalse(verificationResponse.getIsError());
    }

    @Test
    @Order(5)
    @DisplayName("Profile Login")
    void profileLogin() {
        ApiResponse<AuthResponseDTO> loginResponse = authService
                .profileLogin(authRequest, appLanguage);

        assertFalse(loginResponse.getIsError());
        assertEquals(loginResponse.getData().getNickName(), authRequestDTO.getNickName());
        assertFalse(loginResponse.getData().getJwt().isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Reset Password Request")
    void resetPasswordRequest() {
        AuthResetProfileDTO resetProfileDTO = AuthResetProfileDTO
                .builder()
                .phone(authRequestDTO.getPhoneNumber())
                .build();
        ApiResponse<String> resetResponse = authService.resetPasswordRequest(resetProfileDTO);
        assertFalse(resetResponse.getIsError());
    }

    @Test
    @Order(7)
    @DisplayName("Reset Password Confirm Fail Case")
    void reset_password_confirm_fail_case() {
        ResetPasswordConfirmDTO resetPasswordConfirmDTO = ResetPasswordConfirmDTO
                .builder()
                .phone(authRequestDTO.getPhoneNumber())
                .newPassword("123456")
                .repeatNewPassword("123456")
                .smsCode("123456")
                .build();
        ApiResponse<AuthResponseDTO> response = authService
                .resetPasswordConfirm(
                        resetPasswordConfirmDTO,
                        appLanguage
                );

        assertTrue(response.getIsError());
    }

    @Test
    @Order(8)
    @DisplayName("Reset Password Confirm Success Case")
    void reset_password_confirm_success_case() {
        Optional<SmsHistoryEntity> smsHistory = smsHistoryRepository
                .findTopByPhoneOrderByCreatedDateDesc(
                        authRequestDTO.getPhoneNumber()
                );

        assertTrue(smsHistory.isPresent());
        ResetPasswordConfirmDTO resetPasswordConfirmDTO = ResetPasswordConfirmDTO
                .builder()
                .phone(authRequestDTO.getPhoneNumber())
                .newPassword(newPassword)
                .repeatNewPassword(newPassword)
                .smsCode(smsHistory.get().getSmsCode())
                .build();

        ApiResponse<AuthResponseDTO> response = authService
                .resetPasswordConfirm(
                        resetPasswordConfirmDTO,
                        appLanguage
                );

        assertFalse(response.getIsError());
        authRequestDTO.setPassword(newPassword);
        authRequest.setPassword(newPassword);
    }

    @Test
    @Order(9)
    @DisplayName("Profile Login After Change Password")
    void profileLoginAfterChangePassword() {
        authRequest.setPassword(newPassword);
        ApiResponse<AuthResponseDTO> loginResponse = authService
                .profileLogin(authRequest, appLanguage);

        assertFalse(loginResponse.getIsError());
        assertEquals(loginResponse.getData().getNickName(), authRequestDTO.getNickName());
        assertFalse(loginResponse.getData().getJwt().isEmpty());
    }
}