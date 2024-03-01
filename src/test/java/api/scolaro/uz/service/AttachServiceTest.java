package api.scolaro.uz.service;

import api.scolaro.uz.AbstractTestContainers;
import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.SmsDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.auth.AuthRequestDTO;
import api.scolaro.uz.dto.auth.AuthRequestProfileDTO;
import api.scolaro.uz.dto.auth.AuthResponseDTO;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.sms.SmsHistoryEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.repository.sms.SmsHistoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Admin on 24.02.2024
 * @project api.scolaro.uz
 * @package api.scolaro.uz.service
 * @contact @sarvargo
 */
@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AttachServiceTest extends AbstractTestContainers {
    @Autowired
    private AttachService attachService;
    @Autowired
    private AuthService authService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private ProfileService profileService;
    private AuthRequestDTO authRequestDTO;

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
    }

    @Test
    @Order(0)
    @DisplayName("Registration Success Case")
    void registration_success_case() {
        ApiResponse<String> registrationResponse = authService
                .registration(authRequestDTO);
        assertFalse(registrationResponse.getIsError());
    }

    @Test
    @Order(1)
    @DisplayName("Profile Registration Verification Success Case")
    void profile_registration_verification_success_case() {
        String phoneNumber = authRequestDTO.getPhoneNumber();
        Optional<SmsHistoryEntity> smsHistory = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phoneNumber);
        assertTrue(smsHistory.isPresent());

        SmsDTO requestDTO = SmsDTO.builder()
                .phone(phoneNumber)
                .code(smsHistory.get().getSmsCode())
                .build();
        ApiResponse<AuthResponseDTO> verificationResponse = authService.profileRegistrationVerification(requestDTO, AppLanguage.uz);
        assertFalse(verificationResponse.getIsError());
    }

    void login() {
        AuthRequestProfileDTO authRequestDTO = AuthRequestProfileDTO
                .builder()
                .phone("998972322903")
                .password("123456")
                .build();

        ApiResponse<AuthResponseDTO> response = authService
                .profileLogin(authRequestDTO, AppLanguage.uz);

        assertNotNull(response);
        assertFalse(response.getIsError());
        assertNotNull(response.getData());
        assertNotNull(response.getData().getJwt());
        AuthResponseDTO data = response.getData();
        ProfileEntity profileEntity = profileService.get(data.getId());
        assertNotNull(profileEntity);

        UserDetails userDetails = new CustomUserDetails(profileEntity, data.getRoleList());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        authentication
                );
    }

    @Test
    @Order(3)
    @DisplayName("Upload file")
    void upload() throws IOException {
        login();
        Path filePath = Files.createTempFile("test", ".txt");
        Files.write(filePath, "Hello, World!".getBytes());

        try (
                FileInputStream fileInputStream = new FileInputStream(filePath.toFile())
        ) {
            MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", fileInputStream);
            AttachDTO upload = attachService.upload(file);
            assertAll(
                    () -> assertNotNull(upload),
                    () -> assertNotNull(upload.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    @DisplayName("Get all attach")
    void getAll() {
        PageImpl<AttachDTO> allAttach = attachService.getAll(0, 1);
        assertTrue(allAttach.getTotalElements() > 0);
        assertEquals(1, allAttach.getContent().size());
    }

    @Test
    @Order(5)
    @DisplayName("Delete attach success")
    void delete_success() {
        PageImpl<AttachDTO> all = attachService.getAll(0, 1);
        assertTrue(all.getTotalElements() > 0);

        boolean deleted = attachService.delete(all.getContent().get(0).getId());
        assertTrue(deleted);

        PageImpl<AttachDTO> allAttach = attachService.getAll(0, 1);
        assertEquals(0, allAttach.getContent().size());

        assertFalse(allAttach.getTotalElements() > 0);
    }

    @Test
    @Order(6)
    @DisplayName("Delete attach failed")
    void delete_failed() {
        PageImpl<AttachDTO> all = attachService.getAll(0, 1);
        assertTrue(all.getTotalElements() > 0);
        boolean deleted = attachService.delete(all.getContent().get(0).getId());
        assertFalse(deleted);
    }
}