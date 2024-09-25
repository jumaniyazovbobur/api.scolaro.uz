package api.scolaro.uz.service.notification;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.notification.NotificationDTO;
import api.scolaro.uz.dto.notification.NotificationResponseDTO;
import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import api.scolaro.uz.enums.notification.NotificationType;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.repository.notification.NotificationHistoryRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.awt.geom.PathIterator;
import java.io.IOException;
import java.io.InputStream;
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
    @Value("${firebase.api.url}")
    private String url;
    NotificationService( RestTemplate restTemplate,
                        NotificationHistoryRepository notificationHistoryRepository) {
        this.restTemplate = restTemplate;
        this.notificationHistoryRepository = notificationHistoryRepository;
    }


    public void sendTo(NotificationDTO notification) {
        this.sendNotification(notification);
        notificationHistoryRepository.save(toEntity(notification));

    }

    private NotificationHistoryEntity toEntity(NotificationDTO notification) {
        NotificationHistoryEntity entity = new NotificationHistoryEntity();
        entity.setTitle(notification.getTitle());
        entity.setBody(notification.getBody());
        entity.setType(notification.getType());
        entity.setData(notification.getData());
        if (notification.getProfiles() != null) {
            entity.setToProfileId(notification.getProfiles().getToProfile());
            entity.setToProfileType(notification.getProfiles().getToType());
            entity.setFromProfileId(notification.getProfiles().getToProfile());
            entity.setFromProfileType(notification.getProfiles().getToType());
            entity.setFirebaseToken(notification.getToken());
        }
        return entity;
    }

    private void sendNotification(NotificationDTO note) {
        String requestJson = note.toJson();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer %s".formatted(getAccessToken()));

        HttpEntity<String> entity = new HttpEntity<>(requestJson,headers);
        ResponseEntity<String> responseEntity;
        try {
            log.info("Send notification body={}", requestJson);
            responseEntity = restTemplate.postForEntity(url, entity, String.class);
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

    private static String getAccessToken() {
        GoogleCredentials googleCredentials;
        try {
            ClassLoader classLoader = NotificationService.class.getClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream("firebase/scolaro-a0430-firebase-adminsdk-d4ooj-65d7fbb8a1.json")) {

                if (inputStream == null) throw new IllegalArgumentException("File not found! Check the file path.");

                googleCredentials = GoogleCredentials
                        .fromStream(inputStream)
                        .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

                googleCredentials.refresh();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return googleCredentials.getAccessToken().getTokenValue();
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
