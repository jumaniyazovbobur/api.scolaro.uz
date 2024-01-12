package api.scolaro.uz.repository.transaction;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.consulting.ConsultingProfileDTO;
import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsAdminDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsConsultingDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsStudentDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsAdminDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsConsultingDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsStudentDTO;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.enums.transaction.TransactionState;
import api.scolaro.uz.enums.transaction.TransactionStatus;
import api.scolaro.uz.enums.transaction.TransactionType;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 18.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Repository
@RequiredArgsConstructor
public class CustomTransactionRepository {
    private final EntityManager entityManager;
    private final AttachService attachService;

    public FilterResultDTO<TransactionResponseAsStudentDTO> filterAsStudent(TransactionFilterAsStudentDTO filterDTO, String studentId, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        stringBuilder.append(" and t.profile_id = :studentId ");
        params.put("studentId", studentId);
        if (Optional.ofNullable(filterDTO).isPresent()) {
            if (Optional.ofNullable(filterDTO.getType()).isPresent()) {
                stringBuilder.append(" and t.transaction_type = :type ");
                params.put("type", filterDTO.getType().name());
            }
            if (Optional.ofNullable(filterDTO.getFromDate()).isPresent() &&
                    Optional.ofNullable(filterDTO.getToDate()).isPresent()) {
                stringBuilder.append(" and t.created_date between :fromDate and :toDate ");
                params.put("fromDate", filterDTO.getFromDate());
                params.put("toDate", filterDTO.getToDate());
            } else if (Optional.ofNullable(filterDTO.getFromDate()).isPresent()) {
                stringBuilder.append(" and t.created_date >= :fromDate ");
                params.put("fromDate", filterDTO.getFromDate());
            } else if (Optional.ofNullable(filterDTO.getToDate()).isPresent()) {
                stringBuilder.append(" and t.created_date <= :toDate ");
                params.put("toDate", filterDTO.getToDate());
            }
        }

        StringBuilder selectBuilder = new StringBuilder("""
                select t.id, t.amount, t.transaction_type,
                t.status, t.created_date,app.application_number
                from transactions t
                inner join transform tr on tr.id = t.transform_id
                inner join app_application app on app.id = tr.application_id
                where t.visible = TRUE""");
        selectBuilder.append(stringBuilder).append(" order by t.created_date desc ");

        StringBuilder countBuilder = new StringBuilder("""
                select count(*) from transactions as t
                inner join transform tr on tr.id = t.transform_id
                inner join app_application app on app.id = tr.application_id
                where t.visible=true
                """);
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
        List<TransactionResponseAsStudentDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            TransactionResponseAsStudentDTO dto = new TransactionResponseAsStudentDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setAmount(MapperUtil.getLongValue(object[1]));
            dto.setType(MapperUtil.getStringValue(object[2]) != null ? TransactionType.valueOf(MapperUtil.getStringValue(object[2])) : null);
            dto.setStatus(MapperUtil.getStringValue(object[3]) != null ? TransactionStatus.valueOf(MapperUtil.getStringValue(object[3])) : null);
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[4]));
            dto.setApplicationNumber(MapperUtil.getLongValue(object[5]));
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);

    }

    public FilterResultDTO<TransactionResponseAsAdminDTO> filterAsAdmin(TransactionFilterAsAdminDTO filterDTO, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (Optional.ofNullable(filterDTO).isPresent()) {
            if (Optional.ofNullable(filterDTO.getType()).isPresent()) {
                stringBuilder.append(" and t.transaction_type = :type ");
                params.put("type", filterDTO.getType().name());
            }
            if (Optional.ofNullable(filterDTO.getFromDate()).isPresent() &&
                    Optional.ofNullable(filterDTO.getToDate()).isPresent()) {
                stringBuilder.append(" and t.created_date between :fromDate and :toDate ");
                params.put("fromDate", filterDTO.getFromDate());
                params.put("toDate", filterDTO.getToDate());
            } else if (Optional.ofNullable(filterDTO.getFromDate()).isPresent()) {
                stringBuilder.append(" and t.created_date >= :fromDate ");
                params.put("fromDate", filterDTO.getFromDate());
            } else if (Optional.ofNullable(filterDTO.getToDate()).isPresent()) {
                stringBuilder.append(" and t.created_date <= :toDate ");
                params.put("toDate", filterDTO.getToDate());
            }

            if (Optional.ofNullable(filterDTO.getState()).isPresent()) {
                stringBuilder.append(" and t.state <= :state ");
                params.put("state", filterDTO.getState().getValue());
            }
            if (Optional.ofNullable(filterDTO.getStatus()).isPresent()) {
                stringBuilder.append(" and t.status <= :status ");
                params.put("status", filterDTO.getStatus().name());
            }
            if (Optional.ofNullable(filterDTO.getProfileId()).isPresent()) {
                stringBuilder.append(" and t.profile_id <= :profileId ");
                params.put("profileId", filterDTO.getProfileId());
            }
        }

        StringBuilder selectBuilder = new StringBuilder("""
                select t.id, t.amount, t.transaction_type,
                t.status, t.created_date,p.id  pId,
                p.name  pName,p.surname, p.phone,p.photo_id,
                p.created_date pCreatedDate,t.state,cp.id as cpId,
                cp.name as cpName,cp.surname as cpSurname,cp.phone as cpPhone,
                cp.photo_id as cpPhoto,t.profile_type as tProfileType,app.application_number
                from transactions t
                left join profile p on p.id = t.profile_id
                left join consulting_profile cp on cp.id = t.profile_id
                left join transform tr on tr.id = t.transform_id
                left join app_application app on app.id = tr.application_id
                where t.visible = TRUE""");
        selectBuilder.append(stringBuilder).append(" order by t.created_date desc ");

        StringBuilder countBuilder = new StringBuilder("""
                select count(*) from transactions as t where t.visible=true
                """);
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
        List<TransactionResponseAsAdminDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            TransactionResponseAsAdminDTO dto = new TransactionResponseAsAdminDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setAmount(MapperUtil.getLongValue(object[1]));
            dto.setType(MapperUtil.getStringValue(object[2]) != null ? TransactionType.valueOf(MapperUtil.getStringValue(object[2])) : null);
            dto.setStatus(MapperUtil.getStringValue(object[3]) != null ? TransactionStatus.valueOf(MapperUtil.getStringValue(object[3])) : null);
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[4]));
            // profile (student)
            ProfileDTO profile = new ProfileDTO();
            profile.setId(MapperUtil.getStringValue(object[5]));
            profile.setName(MapperUtil.getStringValue(object[6]));
            profile.setSurname(MapperUtil.getStringValue(object[7]));
            profile.setPhone(MapperUtil.getStringValue(object[8]));
            profile.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[9])));
            profile.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[10]));
            dto.setProfile(profile);

            dto.setState(MapperUtil.getStringValue(object[11]) != null ? TransactionState.valueOf(MapperUtil.getStringValue(object[11])) : null);
            // consulting profile
            ConsultingProfileDTO consultingProfile = new ConsultingProfileDTO();
            consultingProfile.setId(MapperUtil.getStringValue(object[12]));
            consultingProfile.setName(MapperUtil.getStringValue(object[13]));
            consultingProfile.setSurname(MapperUtil.getStringValue(object[14]));
            consultingProfile.setPhone(MapperUtil.getStringValue(object[15]));
            consultingProfile.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[16])));
            dto.setConsultingProfile(consultingProfile);
            dto.setProfileType(MapperUtil.getStringValue(object[17]) != null ? ProfileType.valueOf(MapperUtil.getStringValue(object[17])) : null);
            dto.setApplicationNumber(MapperUtil.getLongValue(object[18]));
            // add to list
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);

    }

    public FilterResultDTO<TransactionResponseAsConsultingDTO> filterAsConsulting(TransactionFilterAsConsultingDTO dto, int page, int size, String currentConsultingId) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (Optional.ofNullable(dto).isPresent()) {
            if (Optional.ofNullable(dto.getType()).isPresent()) {
                stringBuilder.append(" and t.transaction_type = :type ");
                params.put("type", dto.getType().name());
            }
            if (Optional.ofNullable(dto.getFromDate()).isPresent() &&
                    Optional.ofNullable(dto.getToDate()).isPresent()) {
                stringBuilder.append(" and t.created_date between :fromDate and :toDate ");
                params.put("fromDate", dto.getFromDate());
                params.put("toDate", dto.getToDate());
            } else if (Optional.ofNullable(dto.getFromDate()).isPresent()) {
                stringBuilder.append(" and t.created_date >= :fromDate ");
                params.put("fromDate", dto.getFromDate());
            } else if (Optional.ofNullable(dto.getToDate()).isPresent()) {
                stringBuilder.append(" and t.created_date <= :toDate ");
                params.put("toDate", dto.getToDate());
            }

            if (Optional.ofNullable(dto.getState()).isPresent()) {
                stringBuilder.append(" and t.state <= :state ");
                params.put("state", dto.getState().getValue());
            }
            if (Optional.ofNullable(dto.getStatus()).isPresent()) {
                stringBuilder.append(" and t.status <= :status ");
                params.put("status", dto.getStatus().name());
            }
            if (Optional.ofNullable(dto.getProfileId()).isPresent()) {
                stringBuilder.append(" and t.profile_id <= :profileId ");
                params.put("profileId", dto.getProfileId());
            }
        }

        StringBuilder selectBuilder = new StringBuilder("""
                select t.id, t.amount, t.transaction_type,
                t.status, t.created_date,p.id  pId,
                p.name  pName,p.surname, p.phone,p.photo_id,
                p.created_date pCreatedDate,t.state,t.profile_type as tProfileType,
                app.application_number
                from transactions t
                left join profile p on p.id = t.profile_id
                left join transform tr on tr.id = t.transform_id
                left join app_application app on app.id = tr.application_id
                where t.visible = TRUE and app.consulting_id = :currentConsultingId""");
        selectBuilder.append(stringBuilder).append(" order by t.created_date desc ");
        params.put("currentConsultingId", currentConsultingId); // current consulting id

        StringBuilder countBuilder = new StringBuilder("""
                select count(*)
                from transactions as t
                left join profile p on p.id = t.profile_id
                left join transform tr on tr.id = t.transform_id
                left join app_application app on app.id = tr.application_id
                where t.visible=true and app.consulting_id = :currentConsultingId
                """);
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
        List<TransactionResponseAsConsultingDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            TransactionResponseAsConsultingDTO item = new TransactionResponseAsConsultingDTO();
            item.setId(MapperUtil.getStringValue(object[0]));
            item.setAmount(MapperUtil.getLongValue(object[1]));
            item.setType(MapperUtil.getStringValue(object[2]) != null ? TransactionType.valueOf(MapperUtil.getStringValue(object[2])) : null);
            item.setStatus(MapperUtil.getStringValue(object[3]) != null ? TransactionStatus.valueOf(MapperUtil.getStringValue(object[3])) : null);
            item.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[4]));
            // profile (student)
            ProfileDTO profile = new ProfileDTO();
            profile.setId(MapperUtil.getStringValue(object[5]));
            profile.setName(MapperUtil.getStringValue(object[6]));
            profile.setSurname(MapperUtil.getStringValue(object[7]));
            profile.setPhone(MapperUtil.getStringValue(object[8]));
            profile.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[9])));
            profile.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[10]));
            item.setProfile(profile);

            item.setState(MapperUtil.getStringValue(object[11]) != null ? TransactionState.valueOf(MapperUtil.getStringValue(object[11])) : null);
            // consulting profile
            item.setProfileType(MapperUtil.getStringValue(object[12]) != null ? ProfileType.valueOf(MapperUtil.getStringValue(object[12])) : null);
            item.setApplicationNumber(MapperUtil.getLongValue(object[13]));
            // add to list
            mapperList.add(item);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }
}
