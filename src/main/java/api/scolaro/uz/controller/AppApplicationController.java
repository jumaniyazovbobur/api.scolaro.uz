package api.scolaro.uz.controller;

import api.scolaro.uz.dto.appApplication.AppApplicationRequestDTO;
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

    @Operation(summary = "AppApplication create", description = "Method user for  AppApplication")
    @GetMapping("")
    public ResponseEntity<?> filter() {
//        log.info("appApplication create {}", dto);

//        appApplicationService.filter();
        return ResponseEntity.ok().build();
    }

}
