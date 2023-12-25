package api.scolaro.uz.service.application;

import api.scolaro.uz.dto.appApplication.AppApplicationLevelAttachDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.entity.application.AppApplicationLevelAttachEntity;
import api.scolaro.uz.enums.AttachType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.appApplication.AppApplicationLevelAttachRepository;
import api.scolaro.uz.service.AttachService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
        entity.setVisible(true);
        appApplicationLevelAttachRepository.save(entity);
        return toDTO(entity, attachDTO);
    }

    public AppApplicationLevelAttachEntity getByAttachId(String attachId) {
        Optional<AppApplicationLevelAttachEntity> optional = appApplicationLevelAttachRepository.findByAttachId(attachId);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
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

    public int deleteLevelAttachByAttachId(String attachId) {
        return appApplicationLevelAttachRepository.deleteByAttachId(attachId);
    }
}
