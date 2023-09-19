package api.scolaro.uz.repository;

import api.dean.db.dto.attach.AttachFilterDTO;
import api.dean.db.dto.attach.AttachResponseDTO;
import api.dean.db.enums.AttachType;
import api.dean.db.enums.CommentType;
import api.dean.db.enums.FileType;
import api.dean.db.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class AttachCustomFilter {
    @Autowired
    private EntityManager entityManager;

    public PageImpl<AttachResponseDTO> filter(AttachFilterDTO filterDTO, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        builder.append(" where a.visible is true ");
        builder.append(" and a.file_type = 'FILE' ");
        if (filterDTO.getName() != null) {
            builder.append(" and lower(a.origen_name) like :origen_name ");
            params.put("origen_name", "%" + filterDTO.getName().toLowerCase().concat("%"));
        }

        StringBuilder selectQueryBuilder = new StringBuilder();

        selectQueryBuilder.append(" select a.id, a.extension, a.origen_name," +
                " a.attach_type, a.given_date, a.comment_type, a.file_type");
        selectQueryBuilder.append(" from attach a ");
        selectQueryBuilder.append(builder);
        selectQueryBuilder.append(" order by a.created_date desc ");
        selectQueryBuilder.append(" limit ").append(size).append(" offset ").append(page * size).append(";");

        String countQueryBuilder = "select count(*)  from attach a " + builder + ";";
        Query selectQuery = entityManager.createNativeQuery(selectQueryBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countQueryBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            if (!param.getKey().equals("lang")) {
                countQuery.setParameter(param.getKey(), param.getValue());
            }
        }

        List<Object[]> apartmentList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        List<AttachResponseDTO> mapperList = new LinkedList<>();
        for (Object[] object : apartmentList) {
            AttachResponseDTO dto = new AttachResponseDTO();
            dto.setId(MapperUtil.getStringValue(object[0]));
            dto.setExtension(MapperUtil.getStringValue(object[1]));
            dto.setOrigenName(MapperUtil.getStringValue(object[2]));
            if (object[3] != null) {
                dto.setAttachType(AttachType.valueOf(MapperUtil.getStringValue(object[3])));
            }
            dto.setGivenDate(MapperUtil.getLocalDateValue(MapperUtil.getStringValue(object[4])));
            if (object[5] != null) {
                dto.setCommentType(CommentType.valueOf(MapperUtil.getStringValue(object[5])));
            }
            if (object[6] != null) {
                dto.setFileType(FileType.valueOf(MapperUtil.getStringValue(object[6])));
            }
            mapperList.add(dto);
        }
        return new PageImpl<>(mapperList, PageRequest.of(page, size), totalCount);
    }


}
