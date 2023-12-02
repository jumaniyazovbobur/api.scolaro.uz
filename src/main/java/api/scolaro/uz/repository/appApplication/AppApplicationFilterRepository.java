package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterConsultingDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationStepDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.application.AppApplicationLevelStatusEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
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
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
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

    /*
     * STUDENT
     */
    public FilterResultDTO<AppApplicationFilterMapperDTO> getApplicationListForStudent(int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String studentId = EntityDetails.getCurrentUserId();
        stringBuilder.append(" and a.student_id =:studentId");
        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select a.id           as appId, " +
                "       a.created_date as appCreatedDate, " +
                "       a.started_date as appStartedDate, " +
                "       a.status       as appStatus, " +
                "       a.application_number       as applicationNumber, " +
                "       c.id           as conId, " +
                "       c.name         as conName, " +
                "       c.photo_id     as conPhotoId, " +
                "       un.id          as uniId, " +
                "       un.name        as uniName, " +
                "       un.photo_id    as uniPhotoId " +
                " from app_application as a " +
                "         inner join consulting as c on a.consulting_id = c.id " +
                "         inner join university as un on a.university_id = un.id " +
                " where a.visible = true ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
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
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setStartedDate(MapperUtil.getLocalDateTimeValue(object[2]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[3])));
            dto.setApplicationNumber(MapperUtil.getLongValue(object[4]));

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[5]));
            consulting.setName(MapperUtil.getStringValue(object[6]));
            consulting.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[7])));
            dto.setConsulting(consulting);

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[8]));
            university.setName(MapperUtil.getStringValue(object[9]));
            university.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[10])));
            dto.setUniversity(university);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

    public FilterResultDTO<AppApplicationFilterMapperDTO> getStudentApplicationConsultingList(int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String studentId = EntityDetails.getCurrentUserId();
        stringBuilder.append(" and a.student_id =:studentId");
        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select " +
                "       a.id           as appId, " +
                "       a.created_date as appCreatedDate, " +
                "       a.status       as appStatus, " +
                "       c.id           as conId, " +
                "       c.name         as conName, " +
                "       c.photo_id     as conPhotoId " +
                " from app_application as a " +
                "         inner join consulting as c on a.consulting_id = c.id " +
                " where a.visible = true ");
        selectBuilder.append(stringBuilder);


        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "inner join consulting as c on a.consulting_id=c.id " +
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
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[2])));

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[3]));
            consulting.setName(MapperUtil.getStringValue(object[4]));
            consulting.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[5])));
            dto.setConsulting(consulting);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

    public FilterResultDTO<AppApplicationFilterMapperDTO> getStudentApplicationUniversityListByConsultingId(String consultingId, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String studentId = EntityDetails.getCurrentUserId();
        params.put("consultingId", consultingId);
        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select a.id,a.application_number as applicationNumber, " +
                "       u.id,u.name,u.photo_id, " +
                "       csl.order_number,csl.id as consultingStepLevelId, " +
                "       ls.id as levelStatusId, ls.deadline, ls.description " +
                "from app_application as a " +
                "inner join university u on u.id = a.university_id " +
                "inner join consulting_step_level csl on csl.id = a.consulting_step_level_id " +
                "inner join app_application_level_status ls on ls.id = a.consulting_step_level_status_id " +
                "where a.visible = true " +
                "     and a.consulting_id =:consultingId  and a.student_id =:studentId ");
        selectBuilder.append(stringBuilder);


        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "where a.visible = true " +
                "  and a.consulting_id =:consultingId " +
                "  and a.student_id =:studentId ");
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
            dto.setApplicationNumber(MapperUtil.getLongValue(object[1]));

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[2]));
            university.setName(MapperUtil.getStringValue(object[3]));
            university.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[4])));
            dto.setUniversity(university);

            ConsultingStepLevelDTO consultingStepLevel = new ConsultingStepLevelDTO();
            consultingStepLevel.setOrderNumber(MapperUtil.getIntegerValue(object[5]));
            consultingStepLevel.setId(MapperUtil.getStringValue(object[6]));
            dto.setConsultingStepLevel(consultingStepLevel);

            AppApplicationLevelStatusDTO applicationLevelStatus = new AppApplicationLevelStatusDTO();
            applicationLevelStatus.setId(MapperUtil.getStringValue(object[7]));
            applicationLevelStatus.setDeadline(MapperUtil.getLocalDateValue(object[8]));
            applicationLevelStatus.setDescription(MapperUtil.getStringValue(object[9]));
            dto.setAppApplicationLevelStatus(applicationLevelStatus);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


    /*
     * CONSULTING
     */
    public FilterResultDTO<AppApplicationFilterMapperDTO> getConsultingApplicationStudentList(int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String consultingId = EntityDetails.getCurrentUserId();
        stringBuilder.append(" and a.consulting_id =:consultingId order by created_date desc");
        params.put("consultingId", consultingId);

        StringBuilder selectBuilder = new StringBuilder("select a.id   as appId, " +
                "       a.created_date                  as appCreatedDate, " +
                "       a.started_date                  as startedDate, " +
                "       a.status                        as appStatus, " +
                "       p.id                            as conId, " +
                "       p.name                          as conName, " +
                "       p.photo_id                      as conPhotoId " +
                "from app_application as a " +
                "         inner join profile as p on p.id = a.student_id " +
                "where a.visible = true ");
        selectBuilder.append(stringBuilder);


        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
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
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[2])));

            String applicationStepLevelStatusStr = MapperUtil.getStringValue(object[3]);
            if (applicationStepLevelStatusStr != null) {
                dto.setApplicationStepLevelStatus(ApplicationStepLevelStatus.valueOf(applicationStepLevelStatusStr));
            }

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[4]));
            consulting.setName(MapperUtil.getStringValue(object[5]));
            consulting.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[6])));
            dto.setConsulting(consulting);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

    public FilterResultDTO<AppApplicationFilterMapperDTO> getFilterApplicationListForConsulting(String consultingId,
                                                                                          AppApplicationFilterConsultingDTO filterDTO, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filterDTO.getName() != null) {
            stringBuilder.append(" and (lower(p.name) like :query or lower(p.surname) like :query) ");
            params.put("query", "%" + filterDTO.getName().toLowerCase() + "%");
        }

        if (filterDTO.getStatus() != null) {
            stringBuilder.append(" and a.status =:status ");
            params.put("status", filterDTO.getStatus().name());
        }
        params.put("consultingId", consultingId);

        StringBuilder selectBuilder = new StringBuilder("select a.id           as appId, " +
                "       a.created_date as appCreatedDate, " +
                "       a.status       as appStatus, " +
                "       un.id          as uniId, " +
                "       un.name        as uniName, " +
                "       un.photo_id    as uniPhotoId, " +
                "       p.id           as sId, " +
                "       p.name         as sName, " +
                "       p.surname      as sSurname, " +
                "       p.photo_id     as sPhotoId, " +
                "       p.phone        as phone " +
                " from app_application as a " +
                "         inner join profile as p on a.student_id = p.id " +
                "         inner join university as un on a.university_id = un.id " +
                " where a.visible = true and a.consulting_id =:consultingId ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "where a.visible = true and a.consulting_id =:consultingId ");
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
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[1]));
            dto.setStatus(AppStatus.valueOf(MapperUtil.getStringValue(object[2])));

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[3]));
            university.setName(MapperUtil.getStringValue(object[4]));
            university.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[5])));
            dto.setUniversity(university);

            ProfileDTO student = new ProfileDTO();
            student.setId(MapperUtil.getStringValue(object[6]));
            student.setName(MapperUtil.getStringValue(object[7]));
            student.setSurname(MapperUtil.getStringValue(object[8]));
            student.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[9])));
            student.setPhone(MapperUtil.getStringValue(object[10]));
            dto.setStudent(student);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

}
