package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consulting.ConsultingCreateDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingIdDTO;
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

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/{id}")
    @Operation(summary = "Get Consulting tariff by id ", description = "for consulting")
    public ResponseEntity<?> getById(@PathVariable String id,@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get Consulting tariff By Id ");
        return ResponseEntity.ok(consultingTariffService.getById(id,language));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PutMapping("/{id}")
    @Operation(summary = "Update consulting tariff", description = "for consulting")
    public ResponseEntity<?> update(@RequestBody ConsultingTariffUpdateDTO dto, @PathVariable String id) {
        log.info("Update consulting tariff {}", dto);
        return ResponseEntity.ok(consultingTariffService.update(dto,id));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete consulting tariff", description = "for consulting")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        log.info("Delete consulting tariff {}", id);
        return ResponseEntity.ok(consultingTariffService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("")
    @Operation(summary = "Get Consulting tariff by consulting  id ", description = "for consulting")
    public ResponseEntity<?> getByConsultingId(@RequestBody ConsultingIdDTO dto, @RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get Consulting tariff By consulting Id ");
        return ResponseEntity.ok(consultingTariffService.getByConsultingId(dto,language));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/template-list")
    @Operation(summary = "Get Consulting tariff Template type ", description = "for consulting")
    public ResponseEntity<?> getTemplateList( @RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get Consulting tariff Template type ");
        return ResponseEntity.ok(consultingTariffService.getTemplateList(language));
    }
}
