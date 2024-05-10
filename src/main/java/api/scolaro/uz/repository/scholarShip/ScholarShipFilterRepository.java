package api.scolaro.uz.repository.scholarShip;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.scholarShip.ScholarShipEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.mapper.ScholarShipMapperDTO;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.scholarShip.ScholarShipDegreeService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ScholarShipFilterRepository {
    private final EntityManager entityManager;

    private final AttachService attachService;
    private final ScholarShipDegreeService scholarShipDegreeService;


    /*
     * FOR ADMIN
     * */
    public FilterResultDTO<ScholarShipMapperDTO> filter(ScholarShipFilterDTO filterDTO, int page, int size, AppLanguage language) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getUniversityName() != null) {
            stringBuilder.append(" and lower(u.name) like :name");
            params.put("name", "%" + filterDTO.getUniversityName().toLowerCase() + "%");
        }

        if (filterDTO.getName() != null) {
            stringBuilder.append(" and lower(s.name) like :scholarShiName");
            params.put("scholarShiName", "%" + filterDTO.getName().toLowerCase() + "%");
        }

        StringBuilder selectBuilder = new StringBuilder("select s.id as sId, " +
                "s.name as sName, " +
                "s.description as sDescr, " + // TODO remove
                "s.price as sPrise, " +
                "s.start_date as sSDate, " +
                "s.expired_date as sEDate, " +
                "s.compressed_photo_id, " +
                "u.id as UId, " +
                "u.name as UName from scholar_ship as s " +
                "inner join university as u on s.university_id=u.id " +
                "where s.visible = true ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) from scholar_ship as s " +
                "inner join university as u on s.university_id=u.id " +
                "where s.visible = true ");
        countBuilder.append(stringBuilder);

        Query selectQuery = entityManager.createNativeQuery(selectBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(size * page); // offset

        // params
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<Object[]> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();
        List<ScholarShipMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            ScholarShipMapperDTO dto = new ScholarShipMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setScholarShipName(MapperUtil.getStringValue(object[1]));
            dto.setDescription(MapperUtil.getStringValue(object[2]));

            dto.setPrice(MapperUtil.getIntegerValue(object[3]));
            dto.setStartDate(MapperUtil.getLocalDateValue(object[4]));
            dto.setExpiredDate(MapperUtil.getLocalDateValue(object[5]));
            dto.setAttachDTO(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[7]));
            university.setName(MapperUtil.getStringValue(object[8]));
            dto.setUniversity(university);
            dto.setDegreeTypeList(scholarShipDegreeService.getScholarShipDegreeTypeList(MapperUtil.getStringValue(object[0]), language));
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


}
