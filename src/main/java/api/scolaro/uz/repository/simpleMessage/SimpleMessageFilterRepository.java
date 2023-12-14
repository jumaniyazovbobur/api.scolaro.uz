package api.scolaro.uz.repository.simpleMessage;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.enums.MessageType;
import api.scolaro.uz.mapper.SimpleMessageMapperDTO;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SimpleMessageFilterRepository {

    private final AttachService attachService;
    private final EntityManager entityManager;
    public FilterResultDTO<SimpleMessageMapperDTO> filterPagination(String applicationId, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (applicationId != null) {
            stringBuilder.append(" and s.application_id=:applicationId ");
            params.put("applicationId", applicationId);
        }

        StringBuilder selectBuilder = new StringBuilder("select s.id as sId, s.created_date as sCreDate, " +
                "s.application_id as sAppId, " +
                "s.attach_id as sPhotoId, " +
                "a.extension as aExtension, " +
                "s.consulting_id as sConId, " +
                "s.profile_id as sStudentId, " +
                "s.is_student_read as sStudentRead, " +
                "s.is_consulting_read as sConsultingRead, " +
                "s.message as sMessage, " +
                "s.message_type as sMessageType " +
                "from simple_message as s " +
                "left join attach as a on s.attach_id=a.id " +
                "where s.visible =true ");
        selectBuilder.append(stringBuilder).append(" order by s.created_date desc ");

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from simple_message as s " +
                "left join attach as a on s.attach_id=a.id " +
                "where s.visible=true ");
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
        List<SimpleMessageMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            SimpleMessageMapperDTO dto = new SimpleMessageMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setApplicationId(MapperUtil.getStringValue(object[2]));
            dto.setAttachDTO(attachService.getResponseAttachWithExtension(MapperUtil.getStringValue(object[3]),MapperUtil.getStringValue(object[4])));
            dto.setConsultingId(MapperUtil.getStringValue(object[5]));
            dto.setStudentId(MapperUtil.getStringValue(object[6]));
            dto.setIsStudentRead(MapperUtil.getVisibleValue(object[7]));
            dto.setIsConsultingRead(MapperUtil.getVisibleValue(object[8]));
            dto.setMessage(MapperUtil.getStringValue(object[9]));
            dto.setMessageType(MessageType.valueOf(MapperUtil.getStringValue(object[10])));
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }
}
