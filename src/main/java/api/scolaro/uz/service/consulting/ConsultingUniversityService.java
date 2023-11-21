package api.scolaro.uz.service.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consulting.ConsultingUniversityDTO;
import api.scolaro.uz.dto.consulting.CountryUniversityResponseDTO;
import api.scolaro.uz.entity.UniversityDegreeTypeEntity;
import api.scolaro.uz.entity.consulting.ConsultingUniversityEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.mapper.ConsultingUniversityMapper;
import api.scolaro.uz.repository.consulting.ConsultingUniversityRepository;
import api.scolaro.uz.service.ResourceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultingUniversityService {
    @Autowired
    private ConsultingUniversityRepository consultingUniversityRepository;
    @Autowired
    private ResourceMessageService resourceMessageService;

    public ApiResponse<?> merger(List<ConsultingUniversityDTO> newList) {
        String consultingId = EntityDetails.getCurrentUserId();
        List<ConsultingUniversityEntity> oldList = consultingUniversityRepository.getConsultingUniversityList(consultingId);
        if (newList != null) {
            newList.forEach(newItem -> {
                Optional<ConsultingUniversityEntity> optional = oldList.stream()
                        .filter(cue -> cue.getUniversityId().equals(newItem.getUniversityId()))
                        .findAny();
                if (optional.isEmpty()) { // not exists
                    create(consultingId, newItem);
                } else if (!optional.get().getTariffId().equals(newItem.getTariffId())) { // exists but with different tariff id
                    updateTariff(consultingId, newItem.getUniversityId(), newItem.getTariffId());
                }
            });
            oldList.forEach(oldItem -> {
                long count = newList.stream()
                        .filter(newItem -> newItem.getUniversityId().equals(oldItem.getUniversityId())).count();
                if (count == 0) {
                    consultingUniversityRepository.deleteByConsultingIdAndUniversityId(consultingId, oldItem.getUniversityId());
                }
            });
        }
        return new ApiResponse<>(resourceMessageService.getMessage("success.insert"), 200, false);
    }

    public void create(String consultingId, ConsultingUniversityDTO dto) {
        ConsultingUniversityEntity entity = new ConsultingUniversityEntity();
        entity.setConsultingId(consultingId);
        entity.setUniversityId(dto.getUniversityId());
        entity.setTariffId(dto.getTariffId());
        consultingUniversityRepository.save(entity);
    }

    public void updateTariff(String consultingId, Long universityId, String tariffId) {
        consultingUniversityRepository.updateTariff(consultingId, universityId, tariffId);
    }

    public List<CountryUniversityResponseDTO> getUniversityListWithConsulting(String consultingId, AppLanguage appLanguage) {
        List<ConsultingUniversityMapper> list = consultingUniversityRepository.getUniversityListWithConsulting(consultingId, appLanguage.name());
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }


    public CountryUniversityResponseDTO toDTO(ConsultingUniversityMapper mapper) {
        CountryUniversityResponseDTO dto = new CountryUniversityResponseDTO();
        dto.setId(mapper.getId());
        dto.setName(mapper.getName());
        dto.setUniversityList(mapper.getUniversityList());
        return dto;
    }

}
