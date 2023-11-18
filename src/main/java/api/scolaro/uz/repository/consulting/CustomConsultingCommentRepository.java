package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseFilterDTO;
import api.scolaro.uz.dto.consultingComment.ConsultingCommentFilterDTO;
import api.scolaro.uz.dto.profile.ProfileResponseFilterDTO;
import api.scolaro.uz.mapper.ConsultingCommentFilterMapperDTO;
import api.scolaro.uz.service.AttachService;
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
public class CustomConsultingCommentRepository {
    private final AttachService attachService;
    private final EntityManager entityManager;

    /*
     * FOR ADMIN
     * */
    public FilterResultDTO<ConsultingCommentFilterMapperDTO> filterForAdmin(ConsultingCommentFilterDTO filterDTO, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        //  filter{studentId,consultingId,createdDate}.
        //  Response (comment{id,createdDate,content, student{id,name,surname, photo{id,url}}, consulting{id,name, photo{id,url}}}
        if (filterDTO.getStudentId() != null) {
            stringBuilder.append(" and cc.student_id = :student_id");
            params.put("student_id", filterDTO.getStudentId());
        }

        if (filterDTO.getConsultingId() != null) {
            stringBuilder.append(" and cc.consulting_id = :consulting_id");
            params.put("consulting_id", filterDTO.getConsultingId());
        }

        if (filterDTO.getFromCreatedDate() != null && filterDTO.getToCreatedDate() != null) {
            stringBuilder.append(" and cc.created_date between :from_created_date and :to_created_date ");
            params.put("from_created_date", filterDTO.getFromCreatedDate());
            params.put("to_created_date", filterDTO.getToCreatedDate());
        } else if (filterDTO.getFromCreatedDate() != null) {
            stringBuilder.append(" and cc.created_date >= :from_created_date ");
            params.put("from_created_date", filterDTO.getFromCreatedDate());
        } else if (filterDTO.getToCreatedDate() != null) {
            stringBuilder.append(" and cc.created_date <= :to_created_date");
            params.put("to_created_date", filterDTO.getToCreatedDate());
        }

        StringBuilder selectBuilder = new StringBuilder("select cc.id as ccId, cc.created_date as ccCreatedDate," +
                "cc.content as ccContent, p.id as pId, p.name as pName, p.surname as pSurname, p.photo_id as pPhotoId," +
                "c.id as cId, c.name as cName, c.photo_id as cPhoto_id" +
                " from consulting as c " +
                " inner join consulting_comment as cc on cc.consulting_id=c.id" +
                " inner join profile as p on p.id=cc.student_id" +
                " where cc.visible = true ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from consulting as c " +
                "inner join consulting_comment as cc on cc.consulting_id=c.id " +
                "inner join profile as p on cc.student_id=p.id " +
                "where cc.visible = true ");
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
        List<ConsultingCommentFilterMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            ConsultingCommentFilterMapperDTO dto = new ConsultingCommentFilterMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setContent(MapperUtil.getStringValue(object[2]));

            ProfileResponseFilterDTO student = new ProfileResponseFilterDTO();
            student.setId(MapperUtil.getStringValue(object[3]));
            student.setName(MapperUtil.getStringValue(object[4]));
            student.setSurname(MapperUtil.getStringValue(object[5]));
            student.setAttach(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));
            dto.setProfile(student);

            ConsultingResponseFilterDTO consulting = new ConsultingResponseFilterDTO();
            consulting.setId(MapperUtil.getStringValue(object[7]));
            consulting.setName(MapperUtil.getStringValue(object[8]));
            consulting.setAttach(attachService.getResponseAttach(MapperUtil.getStringValue(object[9])));
            dto.setConsulting(consulting);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


}
