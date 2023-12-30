package api.scolaro.uz.service.notification;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.notification.NotificationDTO;
import api.scolaro.uz.dto.notification.NotificationResponseDTO;
import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.repository.notification.NotificationHistoryRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page, size);
        String currentUserId = EntityDetails.getCurrentUserId();
        Page<NotificationHistoryEntity> pageEntity = notificationHistoryRepository
                .findAllByIsReadIsFalseAndToProfileId(currentUserId, pageable);

        return ApiResponse
                .ok(
                        new PageImpl<NotificationResponseDTO>(
                                pageEntity
                                        .stream()
                                        .map(NotificationResponseDTO::toDTO)
                                        .toList(),
                                pageable,
                                pageEntity.getTotalElements()
                        )
                );
    }
}