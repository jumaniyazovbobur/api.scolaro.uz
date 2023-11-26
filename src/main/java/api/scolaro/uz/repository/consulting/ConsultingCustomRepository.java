package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.dto.FacultyDTO;
import api.scolaro.uz.dto.FacultyFilterDTO;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingTopFilterDTO;
import api.scolaro.uz.dto.profile.ProfileResponseFilterDTO;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.mapper.ConsultingCommentFilterMapperDTO;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class ConsultingCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<FacultyDTO> publicFilter(FacultyFilterDTO filter, AppLanguage appLanguage, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder("select f.id, case when :lang = 'uz' then f.name_uz when :lang='en' then f.name_en else f.name_ru end as name from faculty as f ");
        StringBuilder countBuilder = new StringBuilder("select count(f) from faculty as f ");
        StringBuilder builder = new StringBuilder(" where f.visible=true ");
        Map<String, Object> params = new LinkedHashMap<>();
        if (filter.getName() != null && !filter.getName().isBlank()) {
            builder.append(" and lower(f.name_uz) like :name  or  lower(f.name_en) like :name or lower(f.name_ru) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }

        countBuilder.append(builder);
        builder.append(" order by f.name ");
        selectBuilder.append(builder);

        Query selectQuery = entityManager.createNativeQuery(selectBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());
        selectQuery.setParameter("lang", appLanguage.name());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<Object[]> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        List<FacultyDTO> facultyDTOList = new LinkedList<>();
        for (Object[] object : entityList) {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setName(MapperUtil.getStringValue(object[1]));
            facultyDTOList.add(dto);
        }
        return new PageImpl<>(facultyDTOList, PageRequest.of(page, size), totalElement);
    }

    public PageImpl<FacultyDTO> adminFilter(FacultyFilterDTO filter, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder("select f.id, f.name_uz, f.name_en, f.name_ru from faculty as f ");
        StringBuilder countBuilder = new StringBuilder("select count(f) from faculty as f ");
        StringBuilder builder = new StringBuilder(" where c.visible=true ");
        Map<String, Object> params = new LinkedHashMap<>();
        if (filter.getName() != null && !filter.getName().isBlank()) {
            builder.append(" and lower(f.name_uz) like :name  or  lower(f.name_en) like :name or lower(f.name_ru) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        countBuilder.append(builder);
        builder.append(" order by f.name ");
        selectBuilder.append(builder);

        Query selectQuery = entityManager.createNamedQuery(selectBuilder.toString());
        Query countQuery = entityManager.createNamedQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<Object[]> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();
        List<FacultyDTO> facultyDTOList = new LinkedList<>();
        for (Object[] object : entityList) {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setNameUz(MapperUtil.getStringValue(object[1]));
            dto.setNameEn(MapperUtil.getStringValue(object[2]));
            dto.setNameRu(MapperUtil.getStringValue(object[3]));
            facultyDTOList.add(dto);
        }
        return new PageImpl<>(facultyDTOList, PageRequest.of(page, size), totalElement);
    }

}
