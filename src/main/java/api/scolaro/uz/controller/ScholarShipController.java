package api.scolaro.uz.controller;

import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipRequestDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipUpdateDTO;
import api.scolaro.uz.service.ScholarShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/scholar-ship")
@Tag(name = "Scholarship Api list", description = "Api list for scholarship")
public class ScholarShipController {

    private final ScholarShipService scholarShipService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ScholarShip create", description = "Method ScholarShip create")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid ScholarShipRequestDTO dto){
        log.info("Create ScholarShip {}" ,dto);
        return ResponseEntity.ok(scholarShipService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ScholarShip get By Id", description = "Method ScholarShip get By Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        log.info("Get ById ScholarShip {}" ,id);
        return ResponseEntity.ok(scholarShipService.getById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ScholarShip update", description = "Method ScholarShip update")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ScholarShipUpdateDTO dto){
        log.info("update ScholarShip {}" ,id);
        return ResponseEntity.ok(scholarShipService.update(id,dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "ScholarShip delete", description = "Method ScholarShip delete")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        log.info("delete ScholarShip {}" ,id);
        return ResponseEntity.ok(scholarShipService.delete(id));
    }

    @Operation(summary = "Filter scholarShip", description = "Method used for filtering scholarShip")
    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                                    @RequestBody ScholarShipFilterDTO dto) {

        log.info("Filtered scholarShipList page={},size={}", page, size);
        return ResponseEntity.ok(scholarShipService.filter(dto,page, size));
    }
}