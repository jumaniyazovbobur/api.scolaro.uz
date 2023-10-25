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
        AppApplicationEntity app = appApplicationService.getByIdForSimpleMessage(dto.getApplicationId());
        AttachEntity attach = attachService.getEntity(dto.getAttachId());

        SimpleMessageEntity entity = new SimpleMessageEntity();
        entity.setMessage(dto.getMessage());
        entity.setStudentId(EntityDetails.getCurrentUserId());
        entity.setAttachId(attach.getId());
        entity.setAppApplicationId(app.getId());
        entity.setMessageType(MessageType.STUDENT);
        simpleMessageRepository.save(entity);
        return new ApiResponse<>("Success", 200, false);
    }


    public ApiResponse<String> createForConsulting(SimpleMessageRequestDTO dto) {
        AppApplicationEntity app = appApplicationService.getByIdForSimpleMessage(dto.getApplicationId());
        AttachEntity attach = attachService.getEntity(dto.getAttachId());

        SimpleMessageEntity entity = new SimpleMessageEntity();
        entity.setMessage(dto.getMessage());
        entity.setConsultingId(EntityDetails.getCurrentUserId());
        entity.setAttachId(attach.getId());
        entity.setAppApplicationId(app.getId());
        entity.setMessageType(MessageType.CONSULTING);
        simpleMessageRepository.save(entity);
        return new ApiResponse<>("Success", 200, false);
    }

    public ApiResponse<List<SimpleMessageResponseDTO>> getListByAppId(String id) {

        AppApplicationEntity app = appApplicationService.getByIdForSimpleMessage(id);

        List<SimpleMessageEntity> getMessageList = simpleMessageRepository.getSimpleMessageListByApplicationId(app.getId());

        List<SimpleMessageResponseDTO> dtoList = new LinkedList<>();
        getMessageList.forEach(entity -> dtoList.add(toDTO(entity)));
        return new ApiResponse<>(200,false,dtoList);
    }

    public ApiResponse<String> updateIsReadStudent(String id){
        AppApplicationEntity app = appApplicationService.getByIdForSimpleMessage(id);
        simpleMessageRepository.updateIsStudentRead(app.getId());
        return new ApiResponse<>(200,false,resourceMessageService.getMessage("success.update"));
    }

    public ApiResponse<String> updateIsReadConsulting(String id){
        AppApplicationEntity app = appApplicationService.getByIdForSimpleMessage(id);
        simpleMessageRepository.updateIsConsultingRead(app.getId());
        return new ApiResponse<>(200,false,resourceMessageService.getMessage("success.update"));
    }
    public SimpleMessageResponseDTO toDTO(SimpleMessageEntity entity) {
        SimpleMessageResponseDTO dto = new SimpleMessageResponseDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setApplicationId(entity.getAppApplicationId());
        dto.setAttachDTO(attachService.getResponseAttach(entity.getAttachId()));
        dto.setConsultingId(entity.getConsultingId());
        dto.setStudentId(entity.getStudentId());
        dto.setIsStudentRead(entity.getIsStudentRead());
        dto.setIsConsultingRead(entity.getIsConsultingRead());
        dto.setMessage(entity.getMessage());
        dto.setMessageType(entity.getMessageType());
        return dto;
    }
}
