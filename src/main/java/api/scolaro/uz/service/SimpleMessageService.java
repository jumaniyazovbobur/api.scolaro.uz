package api.scolaro.uz.service;

import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.notification.NotificationDTO;
import api.scolaro.uz.dto.notification.ProfileInfoDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageRequestDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageResponseDTO;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.SimpleMessageEntity;
import api.scolaro.uz.entity.application.AppApplicationEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.MessageType;
import api.scolaro.uz.enums.notification.NotificationType;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.mapper.SimpleMessageMapperDTO;
import api.scolaro.uz.repository.consulting.ConsultingProfileRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import api.scolaro.uz.repository.simpleMessage.SimpleMessageFilterRepository;
import api.scolaro.uz.repository.simpleMessage.SimpleMessageRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import api.scolaro.uz.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleMessageService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ConsultingProfileRepository consultingProfileRepository;

    @Autowired
    private SimpleMessageRepository simpleMessageRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private AppApplicationService appApplicationService;
    @Autowired
    private ResourceMessageService resourceMessageService;
    @Autowired
    private SimpleMessageFilterRepository simpleMessageFilterRepository;
    private final SimpMessagingTemplate template;
    private final NotificationService notificationService;

    public ApiResponse<SimpleMessageMapperDTO> createForStudent(SimpleMessageRequestDTO dto) {
        AppApplicationEntity app = appApplicationService.getFetchAllData(dto.getApplicationId());
        SimpleMessageEntity entity = new SimpleMessageEntity();
        entity.setMessage(dto.getMessage());
        entity.setProfileId(EntityDetails.getCurrentUserId());
        entity.setAttachId(dto.getAttachId());
        entity.setAppApplicationId(dto.getApplicationId());
        entity.setMessageType(MessageType.STUDENT);
        entity.setIsStudentRead(true);
        simpleMessageRepository.save(entity);

        Optional<ConsultingProfileEntity> consultingProfileOptional = consultingProfileRepository.findById(app.getConsultingProfileId());

        if (consultingProfileOptional.isPresent()) {
            ConsultingProfileEntity consultingProfile = consultingProfileOptional.get();
            sendSocketOrNotification(dto, app, entity, consultingProfile.getIsOnline(), consultingProfile.getFireBaseId());
        }
        return new ApiResponse<>("Success", 200, false,SimpleMessageMapperDTO.toDTO(entity,attachService.getResponseAttach(entity.getAttachId())));
    }

    public ApiResponse<SimpleMessageMapperDTO> createForConsulting(SimpleMessageRequestDTO dto) {
        AppApplicationEntity app = appApplicationService.get(dto.getApplicationId());
        SimpleMessageEntity entity = new SimpleMessageEntity();
        entity.setMessage(dto.getMessage());
        CustomUserDetails customUserDetails = EntityDetails.getCurrentUserDetail();
        entity.setConsultingId(customUserDetails.getProfileConsultingId()); // setConsultingId
        entity.setConsultingProfileId(customUserDetails.getId());
        entity.setAttachId(dto.getAttachId());
        entity.setAppApplicationId(dto.getApplicationId());
        entity.setMessageType(MessageType.CONSULTING);
        entity.setIsConsultingRead(true);
        simpleMessageRepository.save(entity);

        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(app.getStudentId());

        if (profileEntityOptional.isPresent()) {
            ProfileEntity profileEntity = profileEntityOptional.get();
            sendSocketOrNotification(dto, app, entity, profileEntity.getIsOnline(), profileEntity.getFireBaseId());
        }

        return new ApiResponse<>("Success", 200, false,SimpleMessageMapperDTO.toDTO(entity,attachService.getResponseAttach(entity.getAttachId())));
    }

    private void sendSocketOrNotification(SimpleMessageRequestDTO dto, AppApplicationEntity app,
                                          SimpleMessageEntity entity, Boolean isOnline,
                                          String fireBaseId) {
        if (isOnline != null && isOnline) {
            template.convertAndSend("/topic/messages/%s".formatted(entity.getAppApplicationId()),
                    Collections.singleton(SimpleMessageMapperDTO.toDTO(entity, attachService.getResponseAttach(dto.getAttachId())))
            );
        } else if (fireBaseId != null) {
            NotificationDTO notification = new NotificationDTO();
            ProfileEntity student = app.getStudent();
            ConsultingEntity consulting = app.getConsulting();
            notification.getData().put("universityName", app.getUniversity().getName());
            notification.getData().put("consultingName", consulting.getName());
            notification.getData().put("studentFullName", student.getName() + " " + student.getSurname());
            notification.getData().put("studentPhotoUrl", attachService.getUrl(student.getPhotoId()));
            notification.getData().put("consultingPhotoUrl", attachService.getUrl(consulting.getPhotoId()));
            notification.getData().put("applicationId", dto.getApplicationId());
            notification.getData().put("type", NotificationType.CHAT.name());
            notification.setTitle("Chat");
            notification.setType(NotificationType.CHAT);
            notification.setBody(resourceMessageService.getMessage("new.message", AppLanguage.uz));
            notification.setProfiles(
                    Collections
                            .singletonList(new ProfileInfoDTO(
                                    entity.getMessageType().equals(MessageType.STUDENT) ? app.getConsultingProfileId() : app.getStudentId(), entity.getMessageType().equals(MessageType.STUDENT) ? ProfileType.CONSULTING : ProfileType.PROFILE,
                                    EntityDetails.getCurrentUserId(), !entity.getMessageType().equals(MessageType.STUDENT) ? ProfileType.CONSULTING : ProfileType.PROFILE
                            ))
            );
            notification.setRegistrationIds(Collections.singletonList(fireBaseId));
            notificationService.sendTo(notification);
        }
    }

    public ApiResponse<Page<SimpleMessageMapperDTO>> getListByAppApplicationId(String id, int page, int size) {
//        // TODO add pagination, Attach ning type-ni ham qo'shib berish kerak.
        FilterResultDTO<SimpleMessageMapperDTO> filterResult = simpleMessageFilterRepository.filterPagination(id, page, size);
        Page<SimpleMessageMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        notificationService.readAllNotificationByType(NotificationType.CHAT, EntityDetails.getCurrentUserId());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<String> updateIsReadStudent(String id) {
        AppApplicationEntity app = appApplicationService.get(id);
        int countOfMessages = simpleMessageRepository.updateIsStudentRead(app.getId());

        List<SimpleMessageEntity> lastReadMessageAsStudent = simpleMessageRepository.getLastReadMessageAsStudent(id, countOfMessages);

        template.convertAndSend(
                "/topic/messages/%s".formatted(id),
                lastReadMessageAsStudent
                        .stream()
                        .map(item -> SimpleMessageMapperDTO.toDTO(item, attachService.getResponseAttach(item.getAttachId())))
                        .toList()
        );
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.update"));
    }

    public ApiResponse<String> updateIsReadConsulting(String id) {
        AppApplicationEntity app = appApplicationService.get(id);
        int countOfMessages = simpleMessageRepository.updateIsConsultingRead(app.getId());

        List<SimpleMessageEntity> lastReadMessageAsStudent = simpleMessageRepository.getLastReadMessageAsConsulting(id, countOfMessages);

        template.convertAndSend(
                "/topic/messages/%s".formatted(id),
                lastReadMessageAsStudent
                        .stream()
                        .map(item -> SimpleMessageMapperDTO.toDTO(item, attachService.getResponseAttach(item.getAttachId())))
                        .toList()
        );
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.update"));
    }

    public SimpleMessageResponseDTO toDTO(SimpleMessageEntity entity) {
        SimpleMessageResponseDTO dto = new SimpleMessageResponseDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setApplicationId(entity.getAppApplicationId());
        dto.setAttachDTO(attachService.getResponseAttach(entity.getAttachId())); // TODO Attachni type-ni ham qo'shish kerak.
        dto.setConsultingId(entity.getConsultingId());
        dto.setStudentId(entity.getProfileId());
        dto.setIsStudentRead(entity.getIsStudentRead());
        dto.setIsConsultingRead(entity.getIsConsultingRead());
        dto.setMessage(entity.getMessage());
        dto.setMessageType(entity.getMessageType());
        return dto;
    }
}
