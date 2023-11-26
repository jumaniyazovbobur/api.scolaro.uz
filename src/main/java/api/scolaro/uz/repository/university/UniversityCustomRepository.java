package api.scolaro.uz.repository.university;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.university.UniversityFilterDTO;
import api.scolaro.uz.entity.UniversityEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UniversityCustomRepository {

    private final EntityManager entityManager;

    public FilterResultDTO<UniversityEntity> filterPagination(UniversityFilterDTO dto, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder("from UniversityEntity as u");
        StringBuilder countBuilder = new StringBuilder("select count(u) from UniversityEntity as u");
        StringBuilder builder = new StringBuilder(" where u.visible=true");
        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            builder.append(" and lower(u.name) like :name");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }
        if (dto.getCountryId() != null) {
            builder.append(" and u.countryId = :countryId");
            params.put("countryId", dto.getCountryId());
        }

        if (dto.getRating() != null) {
            builder.append(" and u.rating =:rating");
            params.put("rating", dto.getRating());
        }
        countBuilder.append(builder);
        builder.append(" order by u.rating ASC ");
        selectBuilder.append(builder);
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<UniversityEntity> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<UniversityEntity>(entityList, totalElement);

    }

}
