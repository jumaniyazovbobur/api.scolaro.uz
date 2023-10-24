package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterConsultingDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AppApplicationFilterRepository {

    private final AttachService attachService;
    private final EntityManager entityManager;


    /*
     * FOR ADMIN
     * */
    public FilterResultDTO<AppApplicationFilterMapperDTO> filterForAdmin(AppApplicationFilterDTO filterDTO, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getStudentName() != null) {
            stringBuilder.append(" and lower(p.name) like :name");
            params.put("name", "%" + filterDTO.getStudentName().toLowerCase() + "%");
        }
        if (filterDTO.getStudentSurName() != null) {
            stringBuilder.append(" and lower(p.surname) like :surname");
            params.put("surname", "%" + filterDTO.getStudentSurName().toLowerCase() + "%");
        }
        if (filterDTO.getConsultingName() != null) {
            stringBuilder.append(" and lower(c.name) like :cName");
            params.put("cName", "%" + filterDTO.getConsultingName().toLowerCase() + "%");
        }
        if (filterDTO.getStatus() != null) {
            stringBuilder.append(" and a.status =:status");
            params.put("status", filterDTO.getStatus().name());
        }

        StringBuilder selectBuilder = new StringBuilder("select a.id as appId, a.created_date as appCreatedDate, a.visible as appVisible,  " +
                "a.status as appStatus, " +
                "c.id as conId, c.name as conName, c.photo_id as conPhotoId, " +
                "un.name as uniName, un.id as uniId, un.photo_id as uniPhotoId, " +
                "p.id as sId, p.name as sName, p.surname as sSurname,p.photo_id as sPhotoId " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "where a.visible = true ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "where a.visible = true ");
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
        List<AppApplicationFilterMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            AppApplicationFilterMapperDTO dto = new AppApplicationFilterMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateValue(object[1]));
//            dto.setAppVisible(MapperUtil.getVisibleValue(object[2]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[3])));

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[4]));
            consulting.setName(MapperUtil.getStringValue(object[5]));
            consulting.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));
            dto.setConsulting(consulting);

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[8]));
            university.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[9])));
            university.setName(MapperUtil.getStringValue(object[7]));
            dto.setUniversity(university);

            ProfileDTO student = new ProfileDTO();
            student.setId(MapperUtil.getStringValue(object[10]));
            student.setName(MapperUtil.getStringValue(object[11]));
            student.setSurname(MapperUtil.getStringValue(object[12]));
            student.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[13])));
            dto.setStudent(student);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


    public FilterResultDTO<AppApplicationFilterMapperDTO> getForStudent(int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String studentId = EntityDetails.getCurrentUserId();
        stringBuilder.append(" and p.id =:studentId");
        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select a.id as appId, a.created_date as appCreatedDate, a.visible as appVisible,  " +
                "a.status as appStatus, " +
                "c.id as conId, c.name as conName, c.photo_id as conPhotoId, " +
                "un.name as uniName, un.id as uniId, un.photo_id as uniPhotoId " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "where a.visible = true ");
        selectBuilder.append(stringBuilder);


        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "where a.visible = true ");
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
        List<AppApplicationFilterMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            AppApplicationFilterMapperDTO dto = new AppApplicationFilterMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateValue(object[1]));
//            dto.setAppVisible(MapperUtil.getVisibleValue(object[2]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[3])));

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[4]));
            consulting.setName(MapperUtil.getStringValue(object[5]));
            consulting.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));
            dto.setConsulting(consulting);

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setName(MapperUtil.getStringValue(object[7]));
            university.setId(MapperUtil.getLongValue(object[8]));
            university.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[9])));
            dto.setUniversity(university);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


    public FilterResultDTO<AppApplicationFilterMapperDTO> filterForConsulting(AppApplicationFilterConsultingDTO filterDTO, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filterDTO.getStudentName() != null) {
            stringBuilder.append(" and lower(p.name) like :name");
            params.put("name", "%" + filterDTO.getStudentName().toLowerCase() + "%");
        }
        if (filterDTO.getStudentSurName() != null) {
            stringBuilder.append(" and lower(p.surname) like :surname");
            params.put("surname", "%" + filterDTO.getStudentSurName().toLowerCase() + "%");
        }
        if (filterDTO.getStatus() != null) {
            stringBuilder.append(" and a.status =:status");
            params.put("status", filterDTO.getStatus().name());
        }

        StringBuilder selectBuilder = new StringBuilder("select a.id as appId, " +
                "a.created_date as appCreatedDate, " +
                "a.visible as appVisible, " +
                "a.status as appStatus, " +
                "un.name as uniName, " +
                "un.id as uniId, " +
                "un.photo_id as uniPhotoId, " +
                "p.id as sId, " +
                "p.name as sName, " +
                "p.surname as sSurname, " +
                "p.photo_id as sPhotoId " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "where a.visible = true ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "where a.visible = true ");
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
        List<AppApplicationFilterMapperDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            AppApplicationFilterMapperDTO dto = new AppApplicationFilterMapperDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setCreatedDate(MapperUtil.getLocalDateValue(object[1]));
//            dto.setAppVisible(MapperUtil.getVisibleValue(object[2]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[3])));

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setName(MapperUtil.getStringValue(object[4]));
            university.setId(MapperUtil.getLongValue(object[5]));
            university.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));
            dto.setUniversity(university);

            ProfileDTO student = new ProfileDTO();
            student.setId(MapperUtil.getStringValue(object[7]));
            student.setName(MapperUtil.getStringValue(object[8]));
            student.setSurname(MapperUtil.getStringValue(object[9]));
            student.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[10])));
            dto.setStudent(student);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

}
