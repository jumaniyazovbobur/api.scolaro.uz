package api.scolaro.uz.repository.transaction;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsStudentDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsStudentDTO;
import api.scolaro.uz.enums.transaction.TransactionStatus;
import api.scolaro.uz.enums.transaction.TransactionType;
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

    public FilterResultDTO<TransactionResponseAsStudentDTO> filterAsStudent(TransactionFilterAsStudentDTO filterDTO, int page, int size) {
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
        }

        StringBuilder selectBuilder = new StringBuilder("""
                select t.id, t.amount, t.transaction_type,
                t.status, t.created_date
                from transactions t
                where visible = TRUE""");
        selectBuilder.append(stringBuilder).append(" order by s.created_date desc ");

        StringBuilder countBuilder = new StringBuilder("""
                select count(*) from transactions as t where s.visible=true
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
            dto.setCreatedDate(MapperUtil.getLocalDateTimeValue(object[5]));
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);

    }
}
