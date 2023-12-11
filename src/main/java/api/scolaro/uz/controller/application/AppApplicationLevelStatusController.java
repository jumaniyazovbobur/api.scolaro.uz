package api.scolaro.uz.controller.application;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusCreateDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusUpdateDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationRequestDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.AppApplicationLevelStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app-application-level-status")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "App Application level status api list", description = "")
public class AppApplicationLevelStatusController {
    private final AppApplicationLevelStatusService applicationLevelStatusService;

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Create application level status", description = "")
    @PostMapping("")
    public ResponseEntity<ApiResponse<AppApplicationLevelStatusDTO>> create(@RequestBody AppApplicationLevelStatusCreateDTO dto) {
        log.info("Create application level status create {}", dto);
        return ResponseEntity.ok(applicationLevelStatusService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Update application level status", description = "")
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<AppApplicationLevelStatusDTO>> update(@PathVariable("id") String levelStatusId,
                                                                            @RequestBody AppApplicationLevelStatusUpdateDTO dto) {
        log.info("Update application level status create {}", dto);
        return ResponseEntity.ok(applicationLevelStatusService.update(levelStatusId, dto));
    }


    @Operation(summary = "Get application level status list for dropdown", description = "Level status enum list")
    @PostMapping("/anum/list")
    public ResponseEntity<ApiResponse<List<KeyValueDTO>>> update(@RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Get application level status enum list");
        return ResponseEntity.ok(applicationLevelStatusService.getLevelStatuEnumList(language));
    }
}
