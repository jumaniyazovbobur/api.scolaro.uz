package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageRequestDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageResponseDTO;
import api.scolaro.uz.mapper.SimpleMessageMapperDTO;
import api.scolaro.uz.service.SimpleMessageService;
import api.scolaro.uz.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<ApiResponse<SimpleMessageMapperDTO>> createForStudent(@RequestBody @Valid SimpleMessageRequestDTO dto) {
        log.info("student simple message create {}", dto);
        return ResponseEntity.ok(simpleMessageService.createForStudent(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Consulting message create", description = "Method user for consulting message")
    @PostMapping("/consulting")
    public ResponseEntity<ApiResponse<SimpleMessageMapperDTO>> createForConsulting(@RequestBody @Valid SimpleMessageRequestDTO dto) {
        log.info("consulting simple message create {}", dto);
        return ResponseEntity.ok(simpleMessageService.createForConsulting(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_CONSULTING')")
    @Operation(summary = "Get massage list by application id", description = "Method user for get messageList by application Id")
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<ApiResponse<Page<SimpleMessageMapperDTO>>> getListByApplicationId(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                                            @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                                                            @PathVariable String applicationId) {
        log.info("get list by applicationId {}", applicationId);
        return ResponseEntity.ok(simpleMessageService.getListByAppApplicationId(applicationId, PaginationUtil.page(page), size));
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
