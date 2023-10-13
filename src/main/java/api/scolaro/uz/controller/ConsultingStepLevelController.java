package api.scolaro.uz.controller;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelCreateDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelUpdateDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelUpdateResponseDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepCreateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateResponseDTO;
import api.scolaro.uz.service.ConsultingStepLevelService;
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
@RequestMapping("/api/v1/consulting-step-level")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting Step Level api list", description = "api for consulting step level")
public class ConsultingStepLevelController {
    private final ConsultingStepLevelService consultingStepLevelService;

    @PostMapping("/")
    @Operation(summary = "Create Consulting Step Level")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody @Valid ConsultingStepLevelCreateDTO dto) {
        log.info("Create consulting step {}", dto.getNameUz());
        return ResponseEntity.ok(consultingStepLevelService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") String id) {
        log.info("Request for Consulting delete {}", id);
        return ResponseEntity.ok(consultingStepLevelService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "UPDATE Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<ConsultingStepLevelUpdateResponseDTO>> update(@PathVariable("id") String id,
                                                                                    @RequestBody @Valid ConsultingStepLevelUpdateDTO dto) {
        log.info("Request for Consulting update {}", id);
        return ResponseEntity.ok(consultingStepLevelService.update(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get By consulting id")
    public ResponseEntity<ApiResponse<List<ConsultingStepLevelDTO>>> getByConsultingId(@PathVariable("id") String id) {
        log.info("Request for get By Consulting id {}", id);
        return ResponseEntity.ok(consultingStepLevelService.getByConsultingId(id));
    }


}
