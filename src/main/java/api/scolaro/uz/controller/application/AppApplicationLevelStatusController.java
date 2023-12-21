package api.scolaro.uz.controller.application;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusCreateDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusUpdateDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationRequestDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.AppApplicationLevelStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<ApiResponse<AppApplicationLevelStatusDTO>> create(@RequestBody AppApplicationLevelStatusCreateDTO dto,
                                                                            @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Create application level status create {}", dto);
        return ResponseEntity.ok(applicationLevelStatusService.create(dto, language));
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
    @GetMapping("/enum/list")
    public ResponseEntity<ApiResponse<List<KeyValueDTO>>> listForDropDown(@RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Get application level status enum list");
        return ResponseEntity.ok(applicationLevelStatusService.getLevelStatuEnumList(language));
    }

    @Operation(summary = "Finish payment in Application level status", description = "Used to finish payment level status")
    @GetMapping("/payment/finish/{applicationLevelStatusId}")
    public ResponseEntity<ApiResponse<String>> levelStatusFinishPayment(
            @PathParam("applicationLevelStatusId") String applicationLevelStatusId,
            @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Finish payment in Application level status:  {} ", applicationLevelStatusId);
        return ResponseEntity.ok(applicationLevelStatusService.levelStatusFinishPayment(applicationLevelStatusId, language));
    }
}
