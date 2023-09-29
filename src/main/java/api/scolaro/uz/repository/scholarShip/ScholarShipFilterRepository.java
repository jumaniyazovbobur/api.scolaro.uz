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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ScholarShipFilterRepository {
    private final EntityManager entityManager;

    public Page<ScholarShipEntity> filter(ScholarShipFilterDTO fiter, int page, int size) {

        StringBuilder builder = new StringBuilder("SELECT s FROM ScholarShipEntity s");
        StringBuilder countBuilder = new StringBuilder("SELECT  count(p) FROM ScholarShipEntity p");

        Map<String, Object> params = new HashMap<>();
        if (fiter.getVisible() != null) {
            builder.append(" where p.visible= ").append(fiter.getVisible());
            countBuilder.append(" where p.visible = ").append(fiter.getVisible());
        } else {
            builder.append(" where p.visible = true ");
            countBuilder.append(" where p.visible = true ");
        }

       /* if (fiter.getName() != null) {
            builder.append(" and LOWER(c.name) like :name ");
            params.put("name", "%" + fiter.getName().toLowerCase() + "%");
        }*/

        if (fiter.getName() != null) {
            builder.append(" and LOWER(c.name) like :name ");
            countBuilder.append(" And p.name=:name ");
            params.put("name", fiter.getName());
        }


        if (fiter.getDegreeType() != null) {
            builder.append(" And p.degree_type =:type ");
            countBuilder.append(" And p.degree_type =:type ");
            params.put("type", fiter.getDegreeType().name());
        }

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
