package api.scolaro.uz.controller;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffRequestDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffUpdateDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.ConsultingTariffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consulting-tariff")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting Tariff api list", description = "Api list for consulting tariff")
public class ConsultingTariffController {
    private final ConsultingTariffService consultingTariffService;

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PostMapping("")
    @Operation(summary = "Create consulting tariff", description = "for consulting")
    public ResponseEntity<?> create(@RequestBody @Valid ConsultingTariffRequestDTO dto) {
        log.info("Create consulting tariff {}", dto);
        return ResponseEntity.ok(consultingTariffService.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get consulting tariff by id ", description = "for consulting")
    public ResponseEntity<?> getById(@PathVariable String id, @RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get Consulting tariff By Id ");
        return ResponseEntity.ok(consultingTariffService.getById(id, language));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/{id}")
    @Operation(summary = "Update consulting tariff", description = "for consulting")
    public ResponseEntity<?> update(@RequestBody ConsultingTariffUpdateDTO dto, @PathVariable String id) {
        log.info("Update consulting tariff {}", dto);
        return ResponseEntity.ok(consultingTariffService.update(dto, id));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete consulting tariff", description = "for consulting")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        log.info("Delete consulting tariff {}", id);
        return ResponseEntity.ok(consultingTariffService.delete(id));
    }

    @GetMapping("/consulting/{id}")
    @Operation(summary = "Get consulting tariff list by consulting id ", description = "for consulting")
    public ResponseEntity<?> getAllByCurrentConsulting(@PathVariable("id") String consultingId, @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Get consulting tariff list by consultingId ");
        return ResponseEntity.ok(consultingTariffService.getAllByConsultingId(consultingId, language));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/current")
    @Operation(summary = "Get current consulting tariff list", description = "for consulting")
    public ResponseEntity<?> getAllByCurrentConsulting(@RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Get current consulting tariff list");
        return ResponseEntity.ok(consultingTariffService.getAllByConsultingId(EntityDetails.getCurrentUserId(), language));
    }

    @GetMapping("/template-list")
    @Operation(summary = "Get ConsultingTariff list where type is Template", description = "for consulting")
    public ResponseEntity<?> getTemplateList(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get Consulting tariff Template type ");
        return ResponseEntity.ok(consultingTariffService.getTemplateList(language));
    }
}
