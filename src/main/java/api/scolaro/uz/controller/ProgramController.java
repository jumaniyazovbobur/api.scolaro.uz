package api.scolaro.uz.controller;

import api.scolaro.uz.service.ProgramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/program")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Program api list", description = "Api list for program")
public class ProgramController {
    private final ProgramService service;
}
