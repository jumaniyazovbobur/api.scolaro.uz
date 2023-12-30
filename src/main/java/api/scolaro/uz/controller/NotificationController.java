package api.scolaro.uz.controller;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
