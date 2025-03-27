package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.CountryResponse;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.program.ProgramCreateDTO;
import api.scolaro.uz.dto.program.ProgramResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ProgramService;
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
@RequestMapping("/api/v1/program")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Program api")
public class ProgramController {
    private final ProgramService service;

    /**
     * FOR ADMIN
     */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    @Operation(summary = "Create program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody ProgramCreateDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    @Operation(summary = "Update program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody ProgramCreateDTO request) {
        return ResponseEntity.ok(service.update(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete program", description = "for admin")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

//    @GetMapping("/language")
//    @Operation(summary = "Get all program by language", description = "")
//    public ResponseEntity<ApiResponse<List<ProgramResponseDTO>>> allByLanguage(@RequestHeader(value = "Accept-Language",defaultValue = "uz") AppLanguage language) {
//        return ResponseEntity.ok(service.getAllByLanguage(language));
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Get id program ", description = "")
    public ResponseEntity<ApiResponse<ProgramResponseDTO>> getId(@PathVariable("id") Long id,
                                                                 @RequestHeader(value = "Accept-Language",defaultValue = "uz") AppLanguage language) {
        return ResponseEntity.ok(service.getById(id,language));
    }
    // publish-block
    // filter user-lar + pagination
    // filter admin  + pagination

}
