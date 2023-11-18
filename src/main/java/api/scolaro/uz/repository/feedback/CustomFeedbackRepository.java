package api.scolaro.uz.repository.feedback;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.feeback.FeedbackFilterDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.enums.FeedBackType;
import api.scolaro.uz.mapper.FeedbackFilterMapperDTO;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.PersonRoleService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CustomFeedbackRepository {

    private final AttachService attachService;
    private final PersonRoleService personRoleService;
    private final EntityManager entityManager;

    /*
     * FOR ADMIN
     * */
    public FilterResultDTO<FeedbackFilterMapperDTO> filterForAdmin(FeedbackFilterDTO filterDTO, int page, int size) {
        StringBuilder conditionBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        //  filter{studentId,consultingId,createdDate}.
        //  Response (comment{id,createdDate,content, student{id,name,surname, photo{id,url}}, consulting{id,name, photo{id,url}}}

        if (filterDTO.getPersonId() != null) {
            conditionBuilder.append(" and f.person_id = :personId");
            params.put("personId", filterDTO.getPersonId());
        }

        if (filterDTO.getFromDate() != null && filterDTO.getToDate() != null) {
            conditionBuilder.append(" and f.created_date between :from_created_date and :to_created_date ");
            params.put("from_created_date", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MIN));
            params.put("to_created_date", LocalDateTime.of(filterDTO.getToDate(), LocalTime.MAX));
        } else if (filterDTO.getFromDate() != null) {
            conditionBuilder.append(" and f.created_date >= :from_created_date ");
            params.put("from_created_date", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MIN));
        } else if (filterDTO.getToDate() != null) {
            conditionBuilder.append(" and f.created_date <= :to_created_date");
            params.put("to_created_date", LocalDateTime.of(filterDTO.getToDate(), LocalTime.MAX));
        }

        if (filterDTO.getFeedBackType() != null) {
            params.put("feedback_student", filterDTO.getFeedBackType().name());
            params.put("feedback_consulting", filterDTO.getFeedBackType().name());
        } else {
            params.put("feedback_student", "STUDENT");
            params.put("feedback_consulting", "CONSULTING");
        }

        StringBuilder selectBuilder = new StringBuilder();
        selectBuilder.append("select tt.fId, tt.created_date, tt.content, tt.feedback_type, tt.pId, tt.name, tt.surname " +
                " from ( " +
                " select f.id as fId, f.created_date,f.content,f.feedback_type, " +
                " p.id as pId,p.name,p.surname " +
                " from feedback as f " +
                " inner join profile as p on p.id = f.person_id " +
                " and f.feedback_type =:feedback_student ");
        selectBuilder.append(conditionBuilder);
        selectBuilder.append(" UNION ");
        selectBuilder.append(" select f.id, f.created_date,f.content,f.feedback_type, " +
                " c.id,c.owner_name,c.owner_surname " +
                " from feedback as f " +
                " inner join consulting as c on c.id = f.person_id " +
                " and f.feedback_type =:feedback_consulting ");
        selectBuilder.append(conditionBuilder);
        selectBuilder.append(" )  as tt " +
                "order by tt.created_date;");


        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from ( " +
                "select f.id " +
                "from feedback as f " +
                "inner join profile as p on p.id = f.person_id " +
                "and f.feedback_type =:feedback_student ");
        countBuilder.append(conditionBuilder);
        countBuilder.append(" UNION ");
        countBuilder.append("select f.id " +
                "from feedback as f " +
                "inner join consulting as c on c.id = f.person_id " +
                "and f.feedback_type =:feedback_consulting ");
        countBuilder.append(conditionBuilder);
        countBuilder.append(")  as tt; ");

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
        List<FeedbackFilterMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            FeedbackFilterMapperDTO dto = new FeedbackFilterMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setContent(MapperUtil.getStringValue(object[2]));
            dto.setFeedBackType(FeedBackType.valueOf(MapperUtil.getStringValue(object[3])));

            ProfileDTO person = new ProfileDTO();
            person.setId(MapperUtil.getStringValue(object[4]));
            person.setName(MapperUtil.getStringValue(object[5]));
            person.setSurname(MapperUtil.getStringValue(object[6]));
            dto.setPerson(person);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }
}
