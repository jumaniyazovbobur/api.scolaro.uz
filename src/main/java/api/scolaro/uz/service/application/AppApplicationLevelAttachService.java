package api.scolaro.uz.service.application;

import api.scolaro.uz.dto.appApplication.AppApplicationLevelAttachDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.entity.application.AppApplicationLevelAttachEntity;
import api.scolaro.uz.enums.AttachType;
import api.scolaro.uz.repository.appApplication.AppApplicationLevelAttachRepository;
import api.scolaro.uz.service.AttachService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppApplicationLevelAttachService {

    @Autowired
    private AppApplicationLevelAttachRepository appApplicationLevelAttachRepository;
    @Autowired
    private AttachService attachService;

    public AppApplicationLevelAttachDTO createAppLevelAttach(String stepLevelId, AttachDTO attachDTO, AttachType attachType) {
        AppApplicationLevelAttachEntity entity = new AppApplicationLevelAttachEntity();
        entity.setAttachId(attachDTO.getId());
        entity.setConsultingStepLevelId(stepLevelId);
        entity.setAttachType(attachType);
        appApplicationLevelAttachRepository.save(entity);
        return toDTO(entity, attachDTO);
    }

    public AppApplicationLevelAttachDTO toDTO(AppApplicationLevelAttachEntity entity, AttachDTO attachDTO) {
        AppApplicationLevelAttachDTO dto = new AppApplicationLevelAttachDTO();
        dto.setId(entity.getId());
        dto.setAttachType(entity.getAttachType());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setConsultingStepLevelId(entity.getConsultingStepLevelId());
        dto.setAttach(attachDTO);
        return dto;
    }

    public AppApplicationLevelAttachDTO toDTO(AppApplicationLevelAttachEntity entity) {
        AppApplicationLevelAttachDTO dto = new AppApplicationLevelAttachDTO();
        dto.setId(entity.getId());
        dto.setAttachType(entity.getAttachType());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setConsultingStepLevelId(entity.getConsultingStepLevelId());
        dto.setAttach(attachService.getResponseAttach(entity.getAttachId()));
        return dto;
    }
}
