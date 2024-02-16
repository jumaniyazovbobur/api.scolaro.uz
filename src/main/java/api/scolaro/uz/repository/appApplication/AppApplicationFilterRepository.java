package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelResponseDTO;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterConsultingDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationFilterDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationStepDTO;
import api.scolaro.uz.dto.consulting.ConsultingDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.application.AppApplicationLevelStatusEntity;
import api.scolaro.uz.enums.AppLanguage;
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
                "p.id as sId, p.name as sName, p.surname as sSurname,p.photo_id as sPhotoId,a.application_number as aNumber,csl.order_number " +
                "from app_application as a " +
                "inner join profile as p on a.student_id=p.id " +
                "inner join consulting as c on a.consulting_id=c.id " +
                "inner join university as un on a.university_id=un.id " +
                "left join consulting_step_level as csl on a.consulting_step_level_id = csl.id " +
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

            dto.setApplicationNumber(MapperUtil.getLongValue(object[14]));
            ConsultingStepLevelDTO stepLevelDTO = new ConsultingStepLevelDTO();
            stepLevelDTO.setOrderNumber(MapperUtil.getIntegerValue(object[15]));
            dto.setConsultingStepLevel(stepLevelDTO);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

    /*
     * STUDENT
     */
    public FilterResultDTO<AppApplicationFilterMapperDTO> getApplicationListForStudent_web(String studentId, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select a.id                 as appId, " +
                "       a.created_date       as appCreatedDate, " +
                "       a.started_date       as appStartedDate, " +
                "       a.status             as appStatus, " +
                "       a.application_number as applicationNum, " +
                "       (select order_number from consulting_step_level where id = a.consulting_step_level_id) as stepLevelNumber, " +
                "       (select aals.application_step_level_status from app_application_level_status aals where aals.id = a.consulting_step_level_id) as applicationNumber, " +
                "       c.id                 as conId, " +
                "       c.name               as conName, " +
                "       un.id                as uniId, " +
                "       un.name              as uniName " +
                "from app_application as a " +
                "         inner join consulting as c on a.consulting_id = c.id " +
                "         left join university as un on a.university_id = un.id " +
                "where a.visible = true and student_id=:studentId " +
                "order by a.created_date;");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "where a.visible = true and student_id=:studentId ");
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

            ConsultingStepLevelDTO consultingStepLevel = new ConsultingStepLevelDTO();
            consultingStepLevel.setOrderNumber(MapperUtil.getIntegerValue(object[5]));
            dto.setConsultingStepLevel(consultingStepLevel);

            if (object[6] != null) {
                dto.setApplicationStepLevelStatus(ApplicationStepLevelStatus.valueOf(MapperUtil.getStringValue(object[6])));
            }

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[7]));
            consulting.setName(MapperUtil.getStringValue(object[8]));
            dto.setConsulting(consulting);

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[9]));
            university.setName(MapperUtil.getStringValue(object[10]));
            dto.setUniversity(university);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

    public FilterResultDTO<AppApplicationFilterMapperDTO> getApplicationConsultingListForStudent_mobile(int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String studentId = EntityDetails.getCurrentUserId();
        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select c.id  as conId, " +
                "       c.name     as conName, " +
                "       c.photo_id as conPhotoId, " +
                "       (select  count(*) from app_application where consulting_id=c.id and student_id=:studentId and c.visible = true) as universityCount " +
                "        from consulting as c " +
                "        where c.visible = true " +
                "        and c.id in (select consulting_id from app_application " +
                "               where student_id =:studentId and visible = true " +
                "               group by consulting_id)");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from consulting as c " +
                "where c.visible = true " +
                "  and c.id in (select consulting_id " +
                "               from app_application " +
                "               where student_id =:studentId " +
                "                 and visible = true " +
                "               group by consulting_id) ");
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

            ConsultingDTO consulting = new ConsultingDTO();
            consulting.setId(MapperUtil.getStringValue(object[0]));
            consulting.setName(MapperUtil.getStringValue(object[1]));
            consulting.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[2])));
            dto.setConsulting(consulting);

            dto.setUniversityCount(MapperUtil.getLongValue(object[3]));
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

    public FilterResultDTO<AppApplicationFilterMapperDTO> getApplicationUniversityListByConsultingIdForStudent_mobile(String consultingId, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String studentId = EntityDetails.getCurrentUserId();
        params.put("consultingId", consultingId);
        params.put("studentId", studentId);

        StringBuilder selectBuilder = new StringBuilder("select a.id,a.application_number as applicationNumber, " +
                "       u.id,u.name,u.photo_id, " +
                "       csl.order_number,csl.id as consultingStepLevelId, " +
                "       ls.id as levelStatusId, ls.deadline, ls.description, ls.application_step_level_status " +
                "from app_application as a " +
                "left join university u on u.id = a.university_id " +
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
            String applicationStepLevelStatus = MapperUtil.getStringValue(object[10]);
            if (applicationStepLevelStatus != null) {
                applicationLevelStatus.setApplicationStepLevelStatus(ApplicationStepLevelStatus.valueOf(applicationStepLevelStatus));
            }
            dto.setAppApplicationLevelStatus(applicationLevelStatus);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


    /*
     * CONSULTING
     */
    public FilterResultDTO<AppApplicationFilterMapperDTO> getApplicationListForConsulting_web(String consultingId, String consultingProfileId,
                                                                                              AppApplicationFilterConsultingDTO filter, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filter.getQuery() != null) {
            stringBuilder.append(" and ( lower(p.name) like :searchQuery or lower(p.surname) like :searchQuery ) ");
            params.put("searchQuery", "%" + filter.getQuery().toLowerCase() + "%");
        }

        if (filter.getStatus() != null) {
            stringBuilder.append(" and a.status =:status ");
            params.put("status", filter.getStatus().name());
        }
        params.put("consultingId", consultingId);

        if (consultingProfileId != null) {
            stringBuilder.append(" and a.consulting_profile_id =:consultingProfileId ");
            params.put("consultingProfileId", consultingProfileId);
        }

        StringBuilder selectBuilder = new StringBuilder("select a.id as appId, " +
                "       a.created_date  as appCreatedDate, " +
                "       a.started_date  as appStartedDate, " +
                "       a.status        as appStatus, " +
                "       a.application_number  as applicationNum, " +
                "       (select order_number from consulting_step_level where id = a.consulting_step_level_id) as stepLevelNumber, " +
                "       (select aals.application_step_level_status from app_application_level_status aals where aals.id = a.consulting_step_level_id)                                           as applicationNumber, " +
                "       un.id    as uniId, " +
                "       un.name  as uniName, " +
                "       p.id as studentId, " +
                "       p.name as studnetName, " +
                "       p.surname as studentSurname, " +
                "       p.phone as phone " +
                "from app_application as a " +
                "         left join university as un on a.university_id = un.id " +
                "         inner join profile p on p.id = a.student_id " +
                "where a.visible = true " +
                "  and consulting_id = :consultingId ");
        selectBuilder.append(stringBuilder).append(" order by a.created_date ");

        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                "from app_application as a " +
                "inner join profile p on p.id = a.student_id " +
                "where a.visible = true and consulting_id=:consultingId ");
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

            ConsultingStepLevelDTO consultingStepLevel = new ConsultingStepLevelDTO();
            consultingStepLevel.setOrderNumber(MapperUtil.getIntegerValue(object[5]));
            dto.setConsultingStepLevel(consultingStepLevel);

            if (object[6] != null) {
                dto.setApplicationStepLevelStatus(ApplicationStepLevelStatus.valueOf(MapperUtil.getStringValue(object[6])));
            }

            UniversityResponseDTO university = new UniversityResponseDTO();
            university.setId(MapperUtil.getLongValue(object[7]));
            university.setName(MapperUtil.getStringValue(object[8]));
            dto.setUniversity(university);

            ProfileDTO student = new ProfileDTO();
            student.setId(MapperUtil.getStringValue(object[9]));
            student.setName(MapperUtil.getStringValue(object[10]));
            student.setSurname(MapperUtil.getStringValue(object[11]));
            student.setPhone(MapperUtil.getStringValue(object[12]));
            dto.setStudent(student);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


    public FilterResultDTO<AppApplicationFilterMapperDTO> getApplicationStudentListForConsulting_mobile(String consultingId, String consultingProfileId, AppApplicationFilterConsultingDTO filter, Long universityId, int page, int size, AppLanguage language) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("consultingId", consultingId);
        params.put("universityId", universityId);

        if (filter.getQuery() != null) {
            stringBuilder.append(" and (lower(p.name) like :searchQuery or lower(p.surname) like :searchQuery) ");
            params.put("searchQuery", "%" + filter.getQuery().toLowerCase() + "%");
        }

        if (consultingProfileId != null) {
            stringBuilder.append(" and aa.consulting_profile_id =:consultingProfileId ");
            params.put("consultingProfileId", consultingProfileId);
        }

        if (filter.getStatus() != null && !filter.getStatus().equals(AppStatus.ALL)) {
            stringBuilder.append(" and aa.status =:status ");
            params.put("status", filter.getStatus().name());
        }

       /* if (filter.getApplicationStepLevelStatus() != null) {
            stringBuilder.append(" and aa.status =:status ");
            params.put("status", filter.getStatus());
        }*/

        StringBuilder selectBuilder = new StringBuilder(
                "SELECT aa.id as applicationId, " +
                        "(select order_number from consulting_step_level where id = aa.consulting_step_level_id) as stepLevelOrderNumber, " +
                        "(select aals.application_step_level_status from app_application_level_status aals  where aals.id = aa.consulting_step_level_status_id) as stepLevelStatus, " +
                        "p.id,p.name, p.surname, p.phone, p.photo_id " +
                        "from profile as p " +
                        "inner join app_application aa on p.id = aa.student_id " +
                        "where aa.consulting_id =:consultingId and aa.university_id=:universityId " +
                        "and aa.visible = true ");
        selectBuilder.append(stringBuilder);


        StringBuilder countBuilder = new StringBuilder("select count(*) " +
                " from app_application aa  " +
                " inner join profile as p on p.id = aa.student_id " +
                " where aa.consulting_id =:consultingId and aa.university_id=:universityId " +
                "  and aa.visible = true ");
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

            ConsultingStepLevelDTO consultingStepLevelDTO = new ConsultingStepLevelDTO();
            consultingStepLevelDTO.setOrderNumber(MapperUtil.getIntegerValue(object[1]));
            dto.setConsultingStepLevel(consultingStepLevelDTO);

            if (MapperUtil.getStringValue(object[2]) != null) {
                dto.setApplicationStepLevelStatus(ApplicationStepLevelStatus.valueOf(MapperUtil.getStringValue(object[2])));
                dto.setApplicationStepLevelStatusName(dto.getApplicationStepLevelStatus().getName(language));
            }

            ProfileDTO student = new ProfileDTO();
            student.setId(MapperUtil.getStringValue(object[3]));
            student.setName(MapperUtil.getStringValue(object[4]));
            student.setSurname(MapperUtil.getStringValue(object[5]));
            student.setPhone(MapperUtil.getStringValue(object[6]));
            student.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[7])));
            dto.setStudent(student);

            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }


}
