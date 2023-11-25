package api.scolaro.uz.repository.place;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.country.CountryFilterDTO;
import api.scolaro.uz.dto.university.UniversityFilterDTO;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class CountryFilterRepository {
    private final EntityManager entityManager;

    public FilterResultDTO<CountryEntity> filterPagination(CountryFilterDTO dto, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder("from CountryEntity as c");
        StringBuilder countBuilder = new StringBuilder("select count(c) from CountryEntity as c");
        StringBuilder builder = new StringBuilder(" where c.visible=true");
        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            builder.append(" and lower(c.nameUz) like :query or lower(c.nameEn) like :query or lower(c.nameRu) like :query ");
            params.put("query", "%" + dto.getName().toLowerCase() + "%");
        }
        countBuilder.append(builder);
        selectBuilder.append(builder);
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<CountryEntity> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<CountryEntity>(entityList, totalElement);
    }
}
