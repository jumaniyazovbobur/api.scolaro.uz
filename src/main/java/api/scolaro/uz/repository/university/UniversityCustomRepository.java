package api.scolaro.uz.repository.university;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.university.UniversityFilterDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@RequiredArgsConstructor
@Repository
public class UniversityCustomRepository {

    private final EntityManager entityManager;

    @Autowired
    private AttachService attachService;

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

    /*
     * Consulting
     */
    // get application university list for consulting.
    public FilterResultDTO<UniversityResponseDTO> getApplicationUniversityListForConsulting_mobile(String consultingId, int page, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        params.put("consultingId", consultingId);

        StringBuilder selectBuilder = new StringBuilder("select u.id,u.name,u.logo_id, " +
                "       (select count(*) from app_application where consulting_id =:consultingId and university_id = u.id and visible = true) as studentCount " +
                "from university as u " +
                "where u.id in (select university_id from app_application where consulting_id =:consultingId " +
                "               and visible = true group by university_id)");
        selectBuilder.append(stringBuilder);


        StringBuilder countBuilder = new StringBuilder("select count(*)" +
                "from (select university_id from app_application where consulting_id =:consultingId " +
                "  and visible = true group by university_id) as temp_t ");
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
        List<UniversityResponseDTO> mapperList = new LinkedList<>();

        for (Object[] object : entityList) {
            UniversityResponseDTO dto = new UniversityResponseDTO();
            dto.setId(MapperUtil.getLongValue(object[0]));
            dto.setName(MapperUtil.getStringValue(object[1]));
            dto.setLogo(attachService.getResponseAttach(MapperUtil.getStringValue(object[2])));
            dto.setStudentCount(MapperUtil.getLongValue(object[3]));
            mapperList.add(dto);
        }
        return new FilterResultDTO<>(mapperList, totalCount);
    }

}
