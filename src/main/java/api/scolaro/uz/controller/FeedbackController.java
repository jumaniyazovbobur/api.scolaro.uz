package api.scolaro.uz.controller;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentFilterDTO;
import api.scolaro.uz.dto.feeback.FeedbackDTO;
import api.scolaro.uz.dto.feeback.FeedbackFilterDTO;
import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.mapper.FeedbackFilterMapperDTO;
import api.scolaro.uz.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Feedback api list", description = "Api list for feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "Feedback create", description = "Method for Feedback create")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_CONSULTING')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<FeedbackEntity>> create(@RequestBody @Valid FeedbackDTO dto){
        log.info("Feedback create {}", dto);
        return ResponseEntity.ok(feedbackService.create(dto));
    }

    @Operation(summary = "Feedback delete", description = "Method for Feedback delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        log.info("Feedback delete {}", id);
        return ResponseEntity.ok(feedbackService.delete(id));
    }

    /**
     * ADMIN
     */
    @Operation(summary = "Filter AppApplication", description = "Method user for filtering AppApplication")
    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "5") Integer size,
                                    @RequestBody FeedbackFilterDTO dto) {
        log.info("Admin filtered appApplicationList  page={},size={}", page, size);
        return ResponseEntity.ok(feedbackService.filterForAdmin(dto, page, size));
    }


}
