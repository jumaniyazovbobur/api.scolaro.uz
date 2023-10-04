package api.scolaro.uz.repository.scholarShip;

import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.entity.ScholarShipEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ScholarShipFilterRepository {
    private final EntityManager entityManager;

    public Page<ScholarShipEntity> filter(ScholarShipFilterDTO fiter, int page, int size) {

        StringBuilder builder = new StringBuilder("SELECT s FROM ScholarShipEntity s");
        StringBuilder countBuilder = new StringBuilder("SELECT  count(s) FROM ScholarShipEntity s");

        Map<String, Object> params = new HashMap<>();
        if (fiter.getVisible() != null) {
            builder.append(" where s.visible= ").append(fiter.getVisible());
            countBuilder.append(" where s.visible = ").append(fiter.getVisible());
        } else {
            builder.append(" where s.visible = true ");
            countBuilder.append(" where s.visible = true ");
        }

       /* if (fiter.getName() != null) {
            builder.append(" and LOWER(c.name) like :name ");
            params.put("name", "%" + fiter.getName().toLowerCase() + "%");
        }*/

        if (fiter.getName() != null) {
            builder.append(" and LOWER(s.name) like :name ");
            countBuilder.append(" And s.name=:name ");
            params.put("name", fiter.getName());
        }


        if (fiter.getDegreeType() != null) {
            builder.append(" And s.degree_type =:type ");
            countBuilder.append(" And s.degree_type =:type ");
            params.put("type", fiter.getDegreeType().name());
        }

       /*  DATE filter
       if (fiter.getDateFrom() != null && fiter.getDateTo() != null) {
            // 10.07.2023 00:00:00
            // 17.07.2023 23:59:59
            builder.append(" and s.createdDate between :dateFrom and :dateTo ");
            params.put("dateFrom", fiter.getDateFrom());
            params.put("dateTo",  fiter.getDateFrom());
        } else if (fiter.getDateFrom() != null) {
            builder.append(" and s.createdDate >= :dateFrom");
            params.put("dateFrom", fiter.getDateFrom());
        } else if (fiter.getDateTo() != null) {
            builder.append(" and s.createdDate <= :dateTo");
            params.put("dateFrom",fiter.getDateTo());
        }*/
        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page) * size); // 50
        query.setMaxResults(size); // 30
        params.forEach(query::setParameter);

        List<ScholarShipEntity> profileEntityList = query.getResultList();
        // totalCount
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();
        return new PageImpl<>(profileEntityList, PageRequest.of(page, size), totalElements);
    }

}
