package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingStep.ConsultingStepCreateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateResponseDTO;
import api.scolaro.uz.service.ConsultingStepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consulting-step")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting Step api list", description = "api for consulting step")
public class ConsultingStepController {
    private final ConsultingStepService consultingStepService;

    @PostMapping("/")
    @Operation(summary = "Create Consulting Step", description =  "for owner")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody @Valid ConsultingStepCreateDTO dto){
        log.info("Create consulting step {}",dto.getNameUz());
        return ResponseEntity.ok(consultingStepService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") String id){
        log.info("Request for Consulting delete {}", id);
        return ResponseEntity.ok(consultingStepService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "UPDATE Consulting Step", description = "for owner")
    public ResponseEntity<ApiResponse<ConsultingStepUpdateResponseDTO>> update(@PathVariable("id") String id,
                                                                               @RequestBody @Valid ConsultingStepUpdateDTO dto){
        log.info("Request for Consulting update {}", id);
        return ResponseEntity.ok(consultingStepService.update(id, dto));
    }
}
