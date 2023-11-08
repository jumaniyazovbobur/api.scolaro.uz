package api.scolaro.uz.controller.consulting;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingStep.ConsultingStepCreateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffResponseDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.consulting.ConsultingStepService;
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
@RequestMapping("/api/v1/consulting-step")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting Step api list", description = "api for consulting step")
public class ConsultingStepController {
    private final ConsultingStepService consultingStepService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @Operation(summary = "Create Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid ConsultingStepCreateDTO dto) {
        log.info("Create consulting step {}", dto.getName());
        return ResponseEntity.ok(consultingStepService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") String id) {
        log.info("Request for Consulting delete {}", id);
        return ResponseEntity.ok(consultingStepService.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "for owner")
    public ResponseEntity<ApiResponse<ConsultingStepDTO>> getById(@PathVariable("id") String id) {
        log.info("Request for Consulting get by id {}", id);
        return ResponseEntity.ok(consultingStepService.getConsultingById(id));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/{id}")
    @Operation(summary = "UPDATE Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<ConsultingStepDTO>> update(@PathVariable("id") String id,
                                                                 @RequestBody @Valid ConsultingStepUpdateDTO dto) {
        log.info("Request for Consulting update {}", id);
        return ResponseEntity.ok(consultingStepService.update(id, dto));
    }

    @GetMapping("/{id}/level")
    @Operation(summary = "Get consulting step with stepLevel list", description = "for owner")
    public ResponseEntity<ApiResponse<ConsultingStepDTO>> getConsultingDetail(@PathVariable("id") String id,
                                                                              @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Request for Consulting detail {}", id);
        return ResponseEntity.ok(consultingStepService.getConsultingDetail(id, language));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/current")
    @Operation(summary = "Get consulting step list", description = "for owner")
    public ResponseEntity<ApiResponse<List<ConsultingStepDTO>>> getConsultingStepListByRequestedConsulting() {
        log.info("Request for getting consultingStepList by requested consulting");
        return ResponseEntity.ok(consultingStepService.getConsultingStepListByRequestedConsulting());
    }

    @GetMapping("/template-list")
    @Operation(summary = "Get template step list", description = "for consulting")
    public ResponseEntity<ApiResponse<List<ConsultingStepDTO>>> getTemplateList() {
        log.info("Get template tariff list ");
        return ResponseEntity.ok(consultingStepService.getTemplateList());
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/template/{id}/copy")
    @Operation(summary = "Copy template tariff to consulting tariff", description = "for consulting")
    public ResponseEntity<ApiResponse<?>> copyTemplateTariff(@PathVariable("id") String templateStepId) {
        log.info("Copy template consulting step to consulting tariff");
        return ResponseEntity.ok(consultingStepService.copyTemplateToConsultingStep(templateStepId));
    }
}
