package api.scolaro.uz.service;

import api.scolaro.uz.AbstractTestContainers;
import api.scolaro.uz.dto.attach.AttachDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Admin on 24.02.2024
 * @project api.scolaro.uz
 * @package api.scolaro.uz.service
 * @contact @sarvargo
 */
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AttachServiceTest extends AbstractTestContainers {
    @Autowired
    private AttachService attachService;
    private String attachId;

    @Test
    @Order(1)
    @DisplayName("Upload file")
    void upload() throws IOException {

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
            attachId = upload.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    @DisplayName("Get all attach")
    void getAll() {
        PageImpl<AttachDTO> allAttach = attachService.getAll(0, 1);
        assertTrue(allAttach.getTotalElements() > 0);
        assertEquals(1, allAttach.getContent().size());
    }

    @Test
    @Order(3)
    @DisplayName("Delete attach success")
    void delete_success() {
        assertNotNull(attachId);
        boolean deleted = attachService.delete(attachId);
        assertTrue(deleted);

        PageImpl<AttachDTO> allAttach = attachService.getAll(0, 1);
        assertEquals(0, allAttach.getContent().size());

        assertFalse(allAttach.getTotalElements() > 0);
    }

    @Test
    @Order(4)
    @DisplayName("Delete attach failed")
    void delete_failed() {
        boolean deleted = attachService.delete(attachId);
        assertFalse(deleted);
    }
}