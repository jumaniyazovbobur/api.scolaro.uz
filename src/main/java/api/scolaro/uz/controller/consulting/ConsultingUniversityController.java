package api.scolaro.uz.controller.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consulting.ConsultingUniversityDTO;
import api.scolaro.uz.dto.consulting.CountryUniversityResponseDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffRequestDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.consulting.ConsultingTariffService;
import api.scolaro.uz.service.consulting.ConsultingUniversityService;
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
@RequestMapping("/api/v1/consulting-university")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting university api list", description = "Api list for consulting university")
public class ConsultingUniversityController {
    private final ConsultingUniversityService consultingUniversityService;

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @PostMapping("")
    @Operation(summary = "Create consulting university", description = "")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody List<ConsultingUniversityDTO> dtoList) {
        log.info("Create consulting university");
        return ResponseEntity.ok(consultingUniversityService.merger(dtoList));
    }

    @PreAuthorize("hasRole('ROLE_CONSULTING')")
    @GetMapping("/country")
    @Operation(summary = "Get consulting university list", description = "")
    public ResponseEntity<ApiResponse<List<CountryUniversityResponseDTO>>> getConsultingUniversityList(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage language) {
        log.info("Get consulting university list");
        return ResponseEntity.ok(ApiResponse.ok(consultingUniversityService.getUniversityListWithConsulting(language)));
    }
}
