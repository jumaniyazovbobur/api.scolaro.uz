package api.scolaro.uz.controller;

import api.scolaro.uz.dto.consultingComment.ConsultingCommentCreateDTO;
import api.scolaro.uz.service.ConsultingCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consulting/comment")
@Tag(name = "Consulting Comment api list", description = "Api list for Consulting Comment")
public class ConsultingCommentController {

    private final ConsultingCommentService consultingCommentService;

    @Operation(summary = "Consulting Comment create", description = "Method user for Consulting Comment Create")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/{consultingId}")
    public ResponseEntity<?> create(@PathVariable("consultingId") String consultingId, @RequestBody ConsultingCommentCreateDTO dto) {
        log.info("Consulting Comment Create {}", dto);
        return ResponseEntity.ok(consultingCommentService.create(consultingId, dto));
    }

    @Operation(summary = "Consulting Comment get All", description = "Method user for Consulting Comment get All")
    @GetMapping("/{consultingId}")
    public ResponseEntity<?> getAllCommentByConsultingId(@PathVariable("consultingId") String id) {
        log.info("Consulting Comment get By consultingId {}", id);
        return ResponseEntity.ok(consultingCommentService.getByConsultingId(id));
    }

}
