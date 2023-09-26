package api.scolaro.uz.controller;

import api.dean.db.dto.RegionDTO;
import api.dean.db.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/region")
@Tag(name = "Region Api list", description = "Api list for regions")
public class RegionDistinctController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/")
    @Operation(summary = "Create region", description = "")
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.createRegion(dto));
    }

    @DeleteMapping("//{id}")
    @Operation(summary = "Delete region", description = "")
    public Boolean delete(@PathVariable("id") Integer id) {
        return regionService.deleteById(id);
    }

    @PutMapping("//{id}")
    @Operation(summary = "Edit region", description = "")
    public ResponseEntity<RegionDTO> editFaculty(@PathVariable("id") Integer id, @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.updateRegion(dto, id));
    }
}
