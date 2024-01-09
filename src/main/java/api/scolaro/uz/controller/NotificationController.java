package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.notification.NotificationResponseDTO;
import api.scolaro.uz.service.notification.NotificationService;
import api.scolaro.uz.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Admin on 30.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.controller
 * @contact @sarvargo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<ApiResponse<?>> markAsReadById(@PathVariable String id) {
        log.info("Mark as read = {}", id);
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @GetMapping("/not-read")
    public ResponseEntity<ApiResponse<PageImpl<NotificationResponseDTO>>> notRead(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                                  @RequestParam(value = "size", defaultValue = "20") int size) {

        log.info("Find All Not read notification");
        return ResponseEntity.ok(notificationService.findAllByIsReadFalse(PaginationUtil.page(page), size));
    }

    @GetMapping("/not-read-count")
    public ResponseEntity<ApiResponse<Long>> notReadCount() {

        log.info("Find All Not read notification count");
        return ResponseEntity.ok(notificationService.findAllByIsReadFalseCount());
    }
}
