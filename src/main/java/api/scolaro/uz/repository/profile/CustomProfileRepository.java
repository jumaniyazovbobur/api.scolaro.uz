package api.scolaro.uz.repository.profile;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.profile.ProfileFilterDTO;
import api.scolaro.uz.entity.ProfileEntity;
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
public class CustomProfileRepository {

    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<ProfileEntity> filterPagination(ProfileFilterDTO dto, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder("from ProfileEntity as p");
        StringBuilder countBuilder = new StringBuilder("select count(u) from ProfileEntity as p");
        StringBuilder builder = new StringBuilder(" where e.visible=true");
        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            builder.append(" and p.name like :name");
            params.put("name", "'%" + dto.getName() + "%'");
        }
        if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
            builder.append(" and p.surname like :surname");
            params.put("surname", "'%" + dto.getSurname() + "%'");
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            builder.append(" and p.phone = :phone");
            params.put("surname", dto.getPhone());
        }
        if (dto.getFromCreatedDate() != null) {
            builder.append(" and p.createdDate>=:from");
            params.put("from", LocalDateTime.of(dto.getFromCreatedDate(), LocalTime.MIN));
        }
        if (dto.getToCreatedDate() != null) {
            builder.append(" and p.createdDate<=:to");
            params.put("to", LocalDateTime.of(dto.getToCreatedDate(), LocalTime.MAX));
        }
        countBuilder.append(builder);
        builder.append(" order by p.createdDate ");
        selectBuilder.append(builder);
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<ProfileEntity> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<ProfileEntity>(entityList, totalElement);

    }
}
