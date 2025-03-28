package api.scolaro.uz.repository;

import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.program.ProgramFilterDTO;
import api.scolaro.uz.entity.ProgramEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProgramFilterRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<ProgramEntity> getProgramFilterPage(ProgramFilterDTO programFilterDTO, int page, int size) {

        Map<String, Object> params = new HashMap<>();

        StringBuilder query = new StringBuilder();

        if (programFilterDTO.getTitleUz() != null) {
            query.append(" AND p.titleUz=:titleUz");
            params.put("titleUz", programFilterDTO.getTitleUz());
        }
        if (programFilterDTO.getTitleRu() != null) {
            query.append(" AND p.titleRu=:titleRu");
            params.put("titleRu", programFilterDTO.getTitleRu());
        }
        if (programFilterDTO.getTitleEn() != null) {
            query.append(" AND p.titleEn=:titleEn");
            params.put("titleEn", programFilterDTO.getTitleEn());
        }
        if (programFilterDTO.getDestinationId() != null) {
            query.append(" AND p.destinationId=:destinationId");
            params.put("destinationId", programFilterDTO.getDestinationId());
        }
        if (programFilterDTO.getUniversityId() != null) {
//            query.append(" AND p.universityId=:universityId");
            query.append(" AND u.id =:universityId");
            params.put("universityId", programFilterDTO.getUniversityId());
        }
        if (programFilterDTO.getCountryId() != null) {
//            query.append(" AND p.university.countryId=:countryId");
            query.append(" AND c.id =:countryId");
            params.put("countryId", programFilterDTO.getCountryId());
        }
        if (programFilterDTO.getProgramType() != null) {
            query.append(" AND p.programType=:programType");
            params.put("programType", programFilterDTO.getProgramType());
        }
        if (programFilterDTO.getStudyFormat() != null) {
            query.append(" AND p.studyFormat=:studyFormat");
            params.put("studyFormat", programFilterDTO.getStudyFormat());
        }
        if (programFilterDTO.getStudyMode() != null) {
            query.append(" AND p.studyMode=:studyMode");
            params.put("studyMode", programFilterDTO.getStudyMode());
        }
        if (programFilterDTO.getEndDate() != null) {
            query.append(" AND p.endDate <=:endDate ");
            params.put("endDate", programFilterDTO.getEndDate());
        }

        StringBuilder selectSQL = new StringBuilder("FROM ProgramEntity p  inner join univercity as u on u.id = p.univercity_id " +
                "  WHERE 1=1  ");
        StringBuilder countSQL = new StringBuilder("SELECT COUNT(p) FROM ProgramEntity p WHERE 1=1  ");

        if (programFilterDTO.getCountryId() != null) {
            selectSQL.append(" inner join country as c on c.id = u.country_id ");
//            countSQL.....
        }

        selectSQL.append(query);
        countSQL.append(query);

        Query selectQuery = entityManager.createQuery(selectSQL.toString());
        Query countQuery = entityManager.createQuery(countSQL.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List<ProgramEntity> profileList = selectQuery.getResultList();

        Long count = (Long) countQuery.getSingleResult();
        return new FilterResultDTO<>(profileList, count);


    }

}
