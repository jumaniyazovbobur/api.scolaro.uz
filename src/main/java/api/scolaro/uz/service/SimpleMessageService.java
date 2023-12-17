package api.scolaro.uz.service;

import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageRequestDTO;
import api.scolaro.uz.dto.simpleMessage.SimpleMessageResponseDTO;
import api.scolaro.uz.entity.SimpleMessageEntity;
import api.scolaro.uz.entity.application.AppApplicationEntity;
import api.scolaro.uz.enums.MessageType;
import api.scolaro.uz.mapper.SimpleMessageMapperDTO;
import api.scolaro.uz.repository.simpleMessage.SimpleMessageFilterRepository;
import api.scolaro.uz.repository.simpleMessage.SimpleMessageRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleMessageService {

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

        CustomUserDetails customUserDetails = EntityDetails.getCurrentUserDetail();
        entity.setConsultingId(customUserDetails.getProfileConsultingId()); // setConsultingId
        entity.setConsultingProfileId(customUserDetails.getId());
        entity.setAttachId(dto.getAttachId());
        entity.setAppApplicationId(dto.getApplicationId());
        entity.setMessageType(MessageType.CONSULTING);
        entity.setIsConsultingRead(true);
        simpleMessageRepository.save(entity);
        return new ApiResponse<>("Success", 200, false);
    }


    public ApiResponse<Page<SimpleMessageMapperDTO>> getListByAppApplicationId(String id, int page, int size) {
//        // TODO add pagination, Attach ning type-ni ham qo'shib berish kerak.
        FilterResultDTO<SimpleMessageMapperDTO> filterResult = simpleMessageFilterRepository.filterPagination(id, page, size);
        Page<SimpleMessageMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
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
