package api.scolaro.uz.service;

import api.scolaro.uz.AbstractTestContainers;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.auth.AuthRequestDTO;
import api.scolaro.uz.dto.auth.AuthRequestProfileDTO;
import api.scolaro.uz.dto.auth.AuthResetProfileDTO;
import api.scolaro.uz.dto.auth.AuthResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Admin on 23.02.2024
 * @project api.scolaro.uz
 * @package api.scolaro.uz.service
 * @contact @sarvargo
 */
@SpringBootTest
class AuthServiceTest extends AbstractTestContainers {

    @Autowired
    private AuthService authService;

    private AuthRequestDTO authRequestDTO;
    private AuthRequestProfileDTO authRequest;
    private AppLanguage appLanguage;
    private String accessToken;

    @BeforeEach
    void setUp() {
        authRequestDTO = AuthRequestDTO
                .builder()
                .name("Sarvarbek")
                .surname("Mukhtarov")
                .phoneNumber("+998972322903")
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
    }

    @Test
    void registration_success_case() {
        ApiResponse<String> registrationResponse = authService
                .registration(authRequestDTO);
        assertFalse(registrationResponse.getIsError());
    }

    @Test
    void registration_failed_case() {
        ApiResponse<String> registrationResponse = authService
                .registration(authRequestDTO);
        assertTrue(registrationResponse.getIsError());
    }

    @Test
    void profile_registration_verification_success_case() {
        String phoneNumber = authRequestDTO.getPhoneNumber();
        SmsDTO requestDTO = SmsDTO.builder()
                .phone(phoneNumber)
                .code("123456")
                .build();
        ApiResponse<AuthResponseDTO> verificationResponse = authService.profileRegistrationVerification(requestDTO, appLanguage);
        assertFalse(verificationResponse.getIsError());
    }

    @Test
    void profileLogin() {
        ApiResponse<AuthResponseDTO> loginResponse = authService
                .profileLogin(authRequest, appLanguage);

        assertFalse(loginResponse.getIsError());
        assertEquals(loginResponse.getData().getNickName(), authRequestDTO.getNickName());
        assertFalse(loginResponse.getData().getJwt().isEmpty());
    }

    @Test
    void consultingLogin() {

    }

    @Test
    void resetPasswordRequest() {
        AuthResetProfileDTO resetProfileDTO = AuthResetProfileDTO
                .builder()
                .phone(authRequestDTO.getPhoneNumber())
                .build();
        ApiResponse<String> resetResponse = authService.resetPasswordRequest(resetProfileDTO);
        assertFalse(resetResponse.getIsError());
    }

    @Test
    void resetPasswordConfirm() {
    }

    @Test
    void resendSmsCode() {
    }

    @Test
    void resetPasswordConsultingRequest() {
    }

    @Test
    void resetPasswordConsultingConfirm() {
    }
}