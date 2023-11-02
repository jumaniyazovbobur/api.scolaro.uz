package api.scolaro.uz.repository.feedback;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.PersonRoleDTO;
import api.scolaro.uz.dto.feeback.FeedbackFilterDTO;
import api.scolaro.uz.dto.profile.ProfileResponseFilterDTO;
import api.scolaro.uz.mapper.FeedbackFilterMapperDTO;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.PersonRoleService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        //  filter{studentId,consultingId,createdDate}.
        //  Response (comment{id,createdDate,content, student{id,name,surname, photo{id,url}}, consulting{id,name, photo{id,url}}}
        if (filterDTO.getContent() != null) {
            stringBuilder.append(" and f.content = :content");
            params.put("content", filterDTO.getContent());
        }
        if (filterDTO.getPersonId() != null){
            stringBuilder.append(" and f.personId = :personId");
            params.put("personId", filterDTO.getPersonId());
        }

        if (filterDTO.getFeedBackType() != null) {
            stringBuilder.append(" and f.feedback_type = :feedbackType");
            params.put("feedbackType", filterDTO.getFeedBackType());
        }

        if (filterDTO.getFromDate() != null && filterDTO.getToDate() != null) {
            stringBuilder.append(" and f.created_date between :from_created_date and :to_created_date ");
            params.put("from_created_date", filterDTO.getToDate());
            params.put("to_created_date", filterDTO.getFromDate());
        } else if (filterDTO.getFromDate() != null) {
            stringBuilder.append(" and f.created_date >= :from_created_date ");
            params.put("from_created_date", filterDTO.getFromDate());
        } else if (filterDTO.getToDate() != null) {
            stringBuilder.append(" and f.created_date <= :to_created_date");
            params.put("to_created_date", filterDTO.getToDate());
        }

        StringBuilder selectBuilder = new StringBuilder("select f.id as fId, f.created_date as fCreatedDate," +
                "f.content as fContent, p.id as pId, p.name as pName, p.surname as pSurname, p.photo_id as pPhotoId," +
                "pr.id as prId" +
                " from feedback as f " +
                " inner join profile as p on f.person_id = p.id" +
                " inner join person_role as pr on pr.person_id = p.id" +
                " where f.visible = true ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from feedback as f " +
                "inner join profile as p on f.person_id=p.id " +
                "inner join person_role as pr on pr.person_id = p.id " +
                "where f.visible = true ");
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
        List<FeedbackFilterMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            FeedbackFilterMapperDTO dto = new FeedbackFilterMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateValue(object[1]));
            dto.setContent(MapperUtil.getStringValue(object[2]));

            ProfileResponseFilterDTO student = new ProfileResponseFilterDTO();
            student.setId(MapperUtil.getStringValue(object[3]));
            student.setName(MapperUtil.getStringValue(object[4]));
            student.setSurname(MapperUtil.getStringValue(object[5]));
            student.setAttach(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));
            student.setPersonRole(personRoleService.getResponsePerson(MapperUtil.getStringValue(object[7])));
            dto.setPerson(student);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }
}
