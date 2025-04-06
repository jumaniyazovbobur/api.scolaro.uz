package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.webStudent.WebStudentCreateDTO;
import api.scolaro.uz.dto.webStudent.WebStudentResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.WebStudentService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/web-student")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Web student api")
public class WebStudentController {
    private final WebStudentService service;


    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create web student", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody WebStudentCreateDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update web student", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable("id") String id,
                                                      @Valid @RequestBody WebStudentCreateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get by id web student", description = "for admin")
    public ResponseEntity<ApiResponse<WebStudentResponseDTO>> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    @Operation(summary = "Get by list web student", description = "for admin")
    public ResponseEntity<ApiResponse<List<WebStudentResponseDTO>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete web student", description = "")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/language")
    @Operation(summary = "Get by list lang web student", description = "for admin")
    public ResponseEntity<ApiResponse<List<WebStudentResponseDTO>>> getAllLanguage(@RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getAllLanguage(language));
    }


}
