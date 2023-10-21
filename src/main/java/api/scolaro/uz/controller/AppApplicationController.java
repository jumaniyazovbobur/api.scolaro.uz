package api.scolaro.uz.controller;

import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationRequestDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.service.AppApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/app-application")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "App Application api list", description = "Api list for App Application")
public class AppApplicationController {

    private final AppApplicationService appApplicationService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @Operation(summary = "AppApplication create", description = "Method user for  AppApplication")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AppApplicationRequestDTO dto) {
        log.info("appApplication create {}", dto);
        return ResponseEntity.ok(appApplicationService.create(dto));
    }

    @Operation(summary = "Filter AppApplication", description = "Method user for filtering AppApplication")
    @PostMapping("/adm/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                                    @RequestBody AppApplicationFilterDTO dto) {
        log.info("Filtered appApplicationList page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.filterForAdmin(dto, page, size));
    }


    @Operation(summary = "Filter AppApplication for Student", description = "Method user for filtering AppApplication for Student")
    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> filterForStudent(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "5") Integer size) {
        log.info("Filtered appApplicationList for student page={},size={}", page, size);
        return ResponseEntity.ok(appApplicationService.filterForStudent(page, size));
    }
}
