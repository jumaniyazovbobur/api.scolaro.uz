package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.consulting.ConsultingFilterDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.consulting.ConsultingTopFilterDTO;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class CustomConsultingRepository {
    @Autowired
    private AttachService attachService;
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<ConsultingResponseDTO> filterPagination(ConsultingFilterDTO filter, Integer page, Integer size) {
        StringBuilder selectBuilder = new StringBuilder(" select c.id, c.name, c.address, c.photo_id, c.status, " +
                "cp.name,cp.surname, cp.phone, c.status " +
                "from consulting c " +
                "left join  consulting_profile cp on c.manager_id = cp.id " +
                "where c.visible =true ");
        StringBuilder countBuilder = new StringBuilder(" select count(*) " +
                "from consulting c " +
                "left join  consulting_profile cp on c.manager_id = cp.id " +
                "where c.visible =true ");

        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new LinkedHashMap<>();
        if (filter.getName() != null && !filter.getName().isBlank()) {
            builder.append(" and lower(c.name) like :name ");
            params.put("name", "%" + filter.getName().toLowerCase() + "%");
        }
        if (filter.getAddress() != null && !filter.getAddress().isBlank()) {
            builder.append(" and lower(c.address) like :address ");
            params.put("address", "%" + filter.getAddress().toLowerCase() + "%");
        }
        if (filter.getOwnerName() != null && !filter.getOwnerName().isBlank()) {
            builder.append(" and lower(cp.name) like :ownerName ");
            params.put("ownerName", "%" + filter.getOwnerName().toLowerCase() + "%");
        }
        if (filter.getOwnerSurname() != null && !filter.getOwnerSurname().isBlank()) {
            builder.append(" and lower(cp.surname) like :ownerSurname ");
            params.put("ownerSurname", "%" + filter.getOwnerSurname().toLowerCase() + "%");
        }
        if (filter.getPhone() != null && !filter.getPhone().isBlank()) {
            builder.append(" and cp.phone = :phone ");
            params.put("phone", filter.getPhone());
        }
        if (filter.getFromCreatedDate() != null) {
            builder.append(" and c.created_date>=:from ");
            params.put("from", LocalDateTime.of(filter.getFromCreatedDate(), LocalTime.MIN));
        }
        if (filter.getToCreatedDate() != null) {
            builder.append(" and c.created_date<=:to ");
            params.put("to", LocalDateTime.of(filter.getToCreatedDate(), LocalTime.MAX));
        }
        countBuilder.append(builder);
        builder.append(" order by c.created_date ");
        selectBuilder.append(builder);
        Query selectQuery = entityManager.createNativeQuery(selectBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());
        for (Map.Entry<String, Object> p : params.entrySet()) {
            selectQuery.setParameter(p.getKey(), p.getValue());
            countQuery.setParameter(p.getKey(), p.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<Object[]> entityList = selectQuery.getResultList();
        Long totalElement = (Long) countQuery.getSingleResult();

        List<ConsultingResponseDTO> dtoList = new LinkedList<>();
        for (Object[] object : entityList) {
            ConsultingResponseDTO dto = new ConsultingResponseDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setName(MapperUtil.getStringValue(object[1]));
            dto.setAddress(MapperUtil.getStringValue(object[2]));
            dto.setPhoto(attachService.getResponseAttach(MapperUtil.getStringValue(object[3])));
            if (object[4] != null) {
                dto.setStatus(GeneralStatus.valueOf(MapperUtil.getStringValue(object[4])));
            }
            dto.setOwnerName(MapperUtil.getStringValue(object[5]));
            dto.setOwnerSurname(MapperUtil.getStringValue(object[6]));
            dto.setPhone(MapperUtil.getStringValue(object[7]));
            if (MapperUtil.getStringValue(object[8]) != null) {
                dto.setStatus(GeneralStatus.valueOf(MapperUtil.getStringValue(object[8])));
            }
            dtoList.add(dto);
        }

        return new FilterResultDTO<ConsultingResponseDTO>(dtoList, totalElement);
    }

    public FilterResultDTO<ConsultingEntity> filterPaginationForTopConsulting(ConsultingTopFilterDTO dto, Integer page, Integer size) {
        // TODO add countryId and countryName
        StringBuilder selectBuilder = new StringBuilder(" from ConsultingEntity as c left join fetch c.photo ");
        StringBuilder countBuilder = new StringBuilder("select count(c) from ConsultingEntity as c ");
        StringBuilder builder = new StringBuilder(" where c.visible=true and status = 'ACTIVE' ");
        Map<String, Object> params = new LinkedHashMap<>();
        if (dto.getName() != null && !dto.getName().isBlank()) {
            builder.append(" and lower(c.name) like :name ");
            params.put("name", "%" + dto.getName().toLowerCase() + "%");
        }
        countBuilder.append(builder);
        builder.append(" order by c.orderNumber asc ");
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
