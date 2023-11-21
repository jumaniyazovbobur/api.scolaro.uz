package api.scolaro.uz.controller;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FacultyCreateDTO;
import api.scolaro.uz.dto.FacultyDTO;
import api.scolaro.uz.dto.FacultyFilterDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.FacultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculty")
@Tag(name = "Faculty api list", description = "")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @PostMapping("")
    @Operation(summary = "Create faculty", description = "")
    public ResponseEntity<FacultyDTO> create(@RequestBody @Valid FacultyCreateDTO dto) {
        return ResponseEntity.ok(facultyService.create(dto));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Update faculty", description = "")
    public ResponseEntity<FacultyDTO> update(@PathVariable("id") String id, @RequestBody @Valid FacultyCreateDTO dto) {
        return ResponseEntity.ok(facultyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete faculty", description = "")
    public Boolean delete(@PathVariable("id") String id) {
        return facultyService.deleteById(id);
    }

    @PostMapping("/filter/public")
    @Operation(summary = "Public filter ", description = "")
    public ResponseEntity<ApiResponse<List<FacultyDTO>>> filter(@RequestHeader(value = "Accept-Language",
            defaultValue = "uz") AppLanguage appLanguage, @RequestBody FacultyFilterDTO filterDTO) {
        return ResponseEntity.ok(facultyService.filterPublic(appLanguage));
    }

    /**
     * FOR ADMIN
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter/adm")
    @Operation(summary = "Faculty Filter  api", description = "")
    public ResponseEntity<ApiResponse<List<FacultyDTO>>> filter(@RequestBody FacultyFilterDTO filterDTO,
                                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(facultyService.filter(filterDTO, page - 1, size));
    }
}
