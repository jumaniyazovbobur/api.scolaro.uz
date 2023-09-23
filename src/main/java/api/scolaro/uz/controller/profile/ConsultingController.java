package api.scolaro.uz.controller.profile;

import api.scolaro.uz.dto.profile.ConsultingFilterDTO;
import api.scolaro.uz.dto.profile.ConsultingUpdateDTO;
import api.scolaro.uz.service.profile.ConsultingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/consulting")
@Slf4j
@Tag(name = "Consulting api list", description = "Api list for consulting")
public class ConsultingController {
    @Autowired
    private ConsultingService consultingService;
    @PutMapping("/{id}")
    @Operation(summary = "Update api", description = "")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody ConsultingUpdateDTO updateDTO){
        log.info("Update consulting {}", id);
        return ResponseEntity.ok(consultingService.update(id, updateDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id api", description = "")
    public ResponseEntity<?> getId(@PathVariable("id")String id){
        log.info("Get consulting {}", id);
        return ResponseEntity.ok(consultingService.getId(id));

    }

    @GetMapping("/all")
    @Operation(summary = "Get all api", description = "")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "30") int size){
        return ResponseEntity.ok(consultingService.getAll(page - 1, size));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter api", description = "")
    public ResponseEntity<?> filter(@RequestBody ConsultingFilterDTO consultingFilterDTO,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "30") int size){
        return ResponseEntity.ok(consultingService.filter(consultingFilterDTO, page - 1, size));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted consulting api", description = "")
    public ResponseEntity<?> deleted(@PathVariable("id") String id){
        log.info("Deleted consulting {}", id);
        return ResponseEntity.ok(consultingService.deleted(id));
    }

}
