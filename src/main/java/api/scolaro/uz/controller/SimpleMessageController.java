package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageRequestDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageResponseDTO;
import api.scolaro.uz.service.SimpleMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/simple-message")
@Tag(name = "SimpleMessage Api list", description = "Api list for simpleMessage")
public class SimpleMessageController {
    private final SimpleMessageService simpleMessageService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @Operation(summary = "Student message create", description = "Method user for  student message")
    @PostMapping("/student")
    public ResponseEntity<ApiResponse<String>> createForStudent(@RequestBody @Valid SimpleMessageRequestDTO dto) {
        log.info("student simple message create {}", dto);
        return ResponseEntity.ok(simpleMessageService.createForStudent(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Consulting message create", description = "Method user for consulting message")
    @PostMapping("/consulting")
    public ResponseEntity<ApiResponse<String>> createForConsulting(@RequestBody @Valid SimpleMessageRequestDTO dto) {
        log.info("consulting simple message create {}", dto);
        return ResponseEntity.ok(simpleMessageService.createForConsulting(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_CONSULTING')")
    @Operation(summary = "Consulting message create", description = "Method user for consulting message")
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<ApiResponse<List<SimpleMessageResponseDTO>>> getListByAppId(@PathVariable("applicationId") String applicationId) {
        log.info("get list by applicationId {}", applicationId);
        return ResponseEntity.ok(simpleMessageService.getListByAppApplicationId(applicationId));
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    @Operation(summary = "Student messages read", description = "Method user for student messages read by applicationId")
    @PutMapping("/student/mark-as-read/{applicationId}")
    public ResponseEntity<ApiResponse<String>> updateIsReadStudent(@PathVariable("applicationId") String applicationId) {
        log.info("Student messages read {}", applicationId);
        return ResponseEntity.ok(simpleMessageService.updateIsReadStudent(applicationId));
    }

    @PreAuthorize("hasAnyRole('ROLE_CONSULTING')")
    @Operation(summary = "Consulting messages read", description = "Method user for consulting messages read by applicationId")
    @PutMapping("/consulting/mark-as-read/{applicationId}")
    public ResponseEntity<ApiResponse<String>> updateIsReadConsulting(@PathVariable("applicationId") String applicationId) {
        log.info("Student messages read {}", applicationId);
        return ResponseEntity.ok(simpleMessageService.updateIsReadConsulting(applicationId));
    }

}
