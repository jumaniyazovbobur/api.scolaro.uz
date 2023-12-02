package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingTopFilterDTO;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomConsultingRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<ConsultingEntity> filterPagination(ConsultingFilterDTO dto, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder("from ConsultingEntity as c");
        StringBuilder countBuilder = new StringBuilder("select count(c) from ConsultingEntity as c ");
        StringBuilder builder = new StringBuilder(" where c.visible=true ");
        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            builder.append(" and lower(c.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }
        if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
            builder.append(" and lower(c.address) like :address ");
            params.put("address", "%" + dto.getAddress().toLowerCase() + "%");
        }
        if (dto.getOwnerName() != null && !dto.getOwnerName().isBlank()) {
            builder.append(" and lower(c.ownerName) like :ownerName ");
            params.put("ownerName", "%" + dto.getOwnerName().toLowerCase() + "%");
        }
        if (dto.getOwnerSurname() != null && !dto.getOwnerSurname().isBlank()) {
            builder.append(" and lower(c.ownerSurname) like :ownerSurname ");
            params.put("ownerSurname", "%" + dto.getOwnerSurname().toLowerCase() + "%");
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            builder.append(" and c.phone = :phone ");
            params.put("phone", dto.getPhone());
        }
        if (dto.getFromCreatedDate() != null) {
            builder.append(" and c.createdDate>=:from ");
            params.put("from", LocalDateTime.of(dto.getFromCreatedDate(), LocalTime.MIN));
        }
        if (dto.getToCreatedDate() != null) {
            builder.append(" and c.createdDate<=:to ");
            params.put("to", LocalDateTime.of(dto.getToCreatedDate(), LocalTime.MAX));
        }
        countBuilder.append(builder);
        builder.append(" order by c.createdDate ");
        selectBuilder.append(builder);
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<ConsultingEntity> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<ConsultingEntity>(entityList, totalElement);

    }

    public FilterResultDTO<ConsultingEntity> filterPaginationForTopConsulting(ConsultingTopFilterDTO dto, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder(" from ConsultingEntity as c ");
        StringBuilder countBuilder = new StringBuilder("select count(c) from ConsultingEntity as c ");
        StringBuilder builder = new StringBuilder(" where c.visible=true ");
        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            builder.append(" and lower(c.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }
        countBuilder.append(builder);
        builder.append(" order by c.createdDate ");
        selectBuilder.append(builder);
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<ConsultingEntity> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<>(entityList, totalElement);
    }
}
