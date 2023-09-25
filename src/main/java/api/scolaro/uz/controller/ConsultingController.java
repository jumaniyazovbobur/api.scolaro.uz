package api.scolaro.uz.controller;

import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingRegDTO;
import api.scolaro.uz.service.ConsultingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consulting")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Consulting api list", description = "Api list for consulting")
public class ConsultingController {

    private final ConsultingService consultingService;

    @PostMapping("/")
    @Operation(summary = "Create api", description = "")
    public ResponseEntity<?> create(@Valid @RequestBody ConsultingRegDTO dto) {
        log.info("Create consulting {}", dto.getName());
        return ResponseEntity.ok(consultingService.create(dto));
    }

    @PutMapping("/")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<?> update(@Valid @RequestBody ConsultingRegDTO dto) {
        log.info("Update consulting {}", dto.getName());
        return ResponseEntity.ok(consultingService.update(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<?> getId(@PathVariable("id") String id) {
        log.info("Get consulting {}", id);
        return ResponseEntity.ok(consultingService.getId(id));

    }


    @GetMapping("/filter")
    @Operation(summary = "Filter api", description = "")
    public ResponseEntity<?> filter(@RequestBody ConsultingFilterDTO consultingFilterDTO,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(consultingService.filter(consultingFilterDTO, page - 1, size));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted consulting api", description = "")
    public ResponseEntity<?> deleted(@PathVariable("id") String id) {
        log.info("Deleted consulting {}", id);
        return ResponseEntity.ok(consultingService.deleted(id));
    }

}
