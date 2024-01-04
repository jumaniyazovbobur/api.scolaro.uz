package api.scolaro.uz.service.notification;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.notification.NotificationDTO;
import api.scolaro.uz.dto.notification.NotificationResponseDTO;
import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import api.scolaro.uz.enums.notification.NotificationType;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.repository.notification.NotificationHistoryRepository;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Service
@Slf4j
public class NotificationService {

    private final RestTemplate restTemplate;
    private final NotificationHistoryRepository notificationHistoryRepository;

    NotificationService(@Qualifier("RestTemplateFirebase") RestTemplate restTemplate,
                        NotificationHistoryRepository notificationHistoryRepository) {
        this.restTemplate = restTemplate;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }


    public void sendTo(NotificationDTO notification) {
        this.sendNotification(notification);
        // save history
        List<NotificationHistoryEntity> saveList = new ArrayList<>();
        for (int i = 0; i < notification.getRegistrationIds().size(); i++) {
            saveList.add(toEntity(notification, i));
        }
        notificationHistoryRepository.saveAll(saveList);

    }

    private NotificationHistoryEntity toEntity(NotificationDTO notification, int index) {
        NotificationHistoryEntity entity = new NotificationHistoryEntity();
        entity.setTitle(notification.getTitle());
        entity.setBody(notification.getBody());
        entity.setType(notification.getType());
        if (!notification.getProfiles().isEmpty()) {
            entity.setToProfileId(notification.getProfiles().get(index).getToProfile());
            entity.setToProfileType(notification.getProfiles().get(index).getToType());
            entity.setFromProfileId(notification.getProfiles().get(index).getToProfile());
            entity.setFromProfileType(notification.getProfiles().get(index).getToType());
            entity.setFirebaseToken(notification.getRegistrationIds().get(index));
        }
        return entity;
    }

    private void sendNotification(NotificationDTO note) {
        String requestJson = note.toJson();
        HttpEntity<String> entity = new HttpEntity<>(requestJson);
        ResponseEntity<String> responseEntity;
        try {
            log.info("Send notification body={}", requestJson);
            responseEntity = restTemplate.postForEntity("/fcm/send", entity, String.class);
        } catch (Exception e) {
            log.warn("Send notification error={}", e.getMessage());
            return;
        }
        String responseBody = responseEntity.getBody();
        log.info("Success Send notification body = {}", responseBody);
    }

    public ApiResponse<?> markAsRead(String id) {
        notificationHistoryRepository.updateIsRead(id, true);
        return ApiResponse.ok();
    }

    public ApiResponse<PageImpl<NotificationResponseDTO>> findAllByIsReadFalse(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        String currentUserId = EntityDetails.getCurrentUserId();
        Page<NotificationHistoryEntity> pageEntity = notificationHistoryRepository
                .findAllByIsReadIsFalseAndToProfileId(currentUserId, pageable);

        return ApiResponse
                .ok(
                        new PageImpl<>(
                                pageEntity
                                        .stream()
                                        .map(NotificationResponseDTO::toDTO)
                                        .toList(),
                                pageable,
                                pageEntity.getTotalElements()
                        )
                );
    }

    public ApiResponse<Long> findAllByIsReadFalseCount() {
        String currentUserId = EntityDetails.getCurrentUserId();
        Long count = notificationHistoryRepository
                .countByIsReadIsFalseAndToProfileId(currentUserId);

        return ApiResponse
                .ok(
                        count
                );
    }

    public void readAllNotificationByType(NotificationType notificationType, String currentUserId) {
        notificationHistoryRepository.updateIsReadByType(true, notificationType, currentUserId);
    }
}
