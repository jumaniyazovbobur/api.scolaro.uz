package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageRequestDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageResponseDTO;
import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.SimpleMessageEntity;
import api.scolaro.uz.enums.MessageType;
import api.scolaro.uz.repository.SimpleMessageRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleMessageService {

    private final SimpleMessageRepository simpleMessageRepository;
    private final AttachService attachService;
    private final ConsultingService consultingService;
    private final AppApplicationService appApplicationService;
    private final ResourceMessageService resourceMessageService;

    public ApiResponse<String> createForStudent(SimpleMessageRequestDTO dto) {
        AppApplicationEntity app = appApplicationService.get(dto.getApplicationId());
        SimpleMessageEntity entity = new SimpleMessageEntity();
        entity.setMessage(dto.getMessage());
        entity.setProfileId(EntityDetails.getCurrentUserId());
        entity.setAttachId(dto.getAttachId());
        entity.setAppApplicationId(dto.getApplicationId());
        entity.setMessageType(MessageType.STUDENT);
        entity.setIsStudentRead(true);
        simpleMessageRepository.save(entity);
        return new ApiResponse<>("Success", 200, false);
    }


    public ApiResponse<String> createForConsulting(SimpleMessageRequestDTO dto) {
        AppApplicationEntity app = appApplicationService.get(dto.getApplicationId());
        SimpleMessageEntity entity = new SimpleMessageEntity();
        entity.setMessage(dto.getMessage());
        entity.setConsultingId(EntityDetails.getCurrentUserId());
        entity.setAttachId(dto.getAttachId());
        entity.setAppApplicationId(dto.getApplicationId());
        entity.setMessageType(MessageType.CONSULTING);
        entity.setIsConsultingRead(true);
        simpleMessageRepository.save(entity);
        return new ApiResponse<>("Success", 200, false);
    }

    public ApiResponse<List<SimpleMessageResponseDTO>> getListByAppApplicationId(String id) {
        // TODO add pagination, Attach ning type-ni ham qo'shib berish kerak.
        AppApplicationEntity app = appApplicationService.get(id);
        List<SimpleMessageEntity> entityList = simpleMessageRepository.getSimpleMessageListByApplicationId(app.getId());
        List<SimpleMessageResponseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));
        return new ApiResponse<>(200, false, dtoList);
    }

    public ApiResponse<String> updateIsReadStudent(String id) {
        AppApplicationEntity app = appApplicationService.get(id);
        simpleMessageRepository.updateIsStudentRead(app.getId());
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.update"));
    }

    public ApiResponse<String> updateIsReadConsulting(String id) {
        AppApplicationEntity app = appApplicationService.get(id);
        simpleMessageRepository.updateIsConsultingRead(app.getId());
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
