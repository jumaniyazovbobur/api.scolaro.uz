package api.scolaro.uz.controller.consulting;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelCreateDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelUpdateDTO;
import api.scolaro.uz.service.consulting.ConsultingStepLevelService;
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
    @Operation(summary = "Create consulting step level")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody @Valid ConsultingStepLevelCreateDTO dto) {
        log.info("Create consulting step level {}", dto.getNameUz());
        return ResponseEntity.ok(consultingStepLevelService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/{id}")
    @Operation(summary = "Get consulting step level", description = "")
    public ResponseEntity<ApiResponse<ConsultingStepLevelDTO>> getById(@PathVariable("id") String id) {
        log.info("Get consulting stepLevel by id {}", id);
        return ResponseEntity.ok(consultingStepLevelService.getById(id));
    }


    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/{id}")
    @Operation(summary = "Update consulting step", description = "for owner")
    public ResponseEntity<ApiResponse<ConsultingStepLevelDTO>> update(@PathVariable("id") String id,
                                                                      @RequestBody @Valid ConsultingStepLevelUpdateDTO dto) {
        log.info("Request for consulting consulting update {}", id);
        return ResponseEntity.ok(consultingStepLevelService.update(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete consulting step level", description = "for owner")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") String id) {
        log.info("Request for consulting delete {}", id);
        return ResponseEntity.ok(consultingStepLevelService.delete(id));
    }


    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/step/{id}")
    @Operation(summary = "Get By consulting id")
    public ResponseEntity<ApiResponse<List<ConsultingStepLevelDTO>>> getConsultingStepLevelListByConsultingStepId(@PathVariable("id") String stepLevelId) {
        log.info("Request for get By Consulting id {}", stepLevelId);
        return ResponseEntity.ok(ApiResponse.ok(consultingStepLevelService.getConsultingStepLevelListByConsultingStepId(stepLevelId)));
    }


}
