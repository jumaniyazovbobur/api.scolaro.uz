package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.service.UniversityDegreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/university-degree")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "University degree api list", description = "Api list for university degree")
public class UniversityDegreeTypeController {
    private final UniversityDegreeService universityDegreeService;

    @GetMapping("")
    @Operation(summary = "Get university-degree list", description = "")
    public ResponseEntity<ApiResponse<List<KeyValueDTO>>> getAll(@RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        log.info("Get university degree list");
        return ResponseEntity.ok(universityDegreeService.getList(language));
    }
}
