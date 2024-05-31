package api.scolaro.uz.repository.search;

import api.scolaro.uz.dto.CustomPaginationDTO;
import api.scolaro.uz.dto.search.SearchResponseDTO;
import api.scolaro.uz.dto.search.res.SearchFilterResDTO;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.11.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class SearchRepository {
    private final EntityManager entityManager;
    private final AttachService attachService;

    public CustomPaginationForSearch search(SearchFilterResDTO dto, String lang, int page, int size) {
        if (dto == null) {
            return new CustomPaginationForSearch();
        }
        if (dto.getQuery() == null
                && dto.getFacultyId() == null
                && dto.getCountryId() == null
                && dto.getContinentId() == null
        ) {
            return new CustomPaginationForSearch();
        }

        boolean isQuery = false;
        Map<String, Object> params = new HashMap<>();

        if (dto.getQuery() != null) {
            isQuery = true;
            dto.setQuery("%" + dto.getQuery() + "%");
            params.put("query", dto.getQuery());
        }
        // ------------------------
        String selectSql = prepareSelectQuery(isQuery, dto, params);
        String countSql = prepareCountQuery(isQuery, dto, params);
        // ------------------------------------------------------------------------
        Query selectQuery = entityManager.createNativeQuery(selectSql);
        Query countQuery = entityManager.createNativeQuery(countSql);
        // ----------------------
//        selectQuery.setParameter("lang", lang);
        selectQuery.setParameter("limit", size);
        selectQuery.setParameter("offset", (page * size));
        // ----------
        params.forEach((key, value) -> {
            countQuery.setParameter(key, value);
            selectQuery.setParameter(key, value);
        });
        //
        List<Object[]> selectQueryResultList = selectQuery.getResultList();
        List<Object> countQueryResultList = countQuery.getResultList();
        //
        Long scholarshipTotalCount = 0L; // SCHOLARSHIP
        Long consultingTotalCount = 0L; // CONSULTING
        Long universityTotalCount = 0L; // UNIVERSITY

        if (dto.getSearchType() == null) {
            scholarshipTotalCount = (Long) countQueryResultList.get(0); // SCHOLARSHIP
            consultingTotalCount = (Long) countQueryResultList.get(1); // CONSULTING
            universityTotalCount = (Long) countQueryResultList.get(2); // UNIVERSITY
        } else if (dto.getSearchType().equals("SCHOLARSHIP")) {
            scholarshipTotalCount = (Long) countQueryResultList.get(0); // SCHOLARSHIP
        } else if (dto.getSearchType().equals("CONSULTING")) {
            consultingTotalCount = (Long) countQueryResultList.get(0); // CONSULTING
        } else if (dto.getSearchType().equals("UNIVERSITY")) {
            universityTotalCount = (Long) countQueryResultList.get(0); // UNIVERSITY
        }

        List<SearchResponseDTO> resultList = selectQueryResultList
                .stream()
                .map(this::convert)
                .toList();

        CustomPaginationForSearch res = new CustomPaginationForSearch();
        Long finalScholarshipTotalCount = scholarshipTotalCount;
        Long finalConsultingTotalCount = consultingTotalCount;
        Long finalUniversityTotalCount = universityTotalCount;
        parseToCustomPagination(resultList)
                .forEach((string, searchResponseDTOS) -> {
                    switch (string) {
                        case "SCHOLARSHIP" -> {
                            res.setScholar(new CustomPaginationDTO<>(finalScholarshipTotalCount, searchResponseDTOS));
                        }
                        case "CONSULTING" -> {
                            res.setConsulting(new CustomPaginationDTO<>(finalConsultingTotalCount, searchResponseDTOS));
                        }
                        case "UNIVERSITY" -> {
                            res.setUniversity(new CustomPaginationDTO<>(finalUniversityTotalCount, searchResponseDTOS));
                        }
                    }
                });
        return res;
    }

    private SearchResponseDTO convert(Object[] object) {
        // id, name, type, image_id, description
        String imageId = MapperUtil.getStringValue(object[3]);
        String description = MapperUtil.getStringValue(object[4]);
        return new SearchResponseDTO(
                MapperUtil.getStringValue(object[0]),
                MapperUtil.getStringValue(object[1]),
                MapperUtil.getStringValue(object[2]),
                imageId == null ? null : attachService.getUrl(imageId),
                description);
    }

    private String prepareSelectQuery(Boolean isQuery, SearchFilterResDTO dto, Map<String, Object> params) {
        Set<String> searchingTableSet = new LinkedHashSet<>();
        // ----------------------------------------------------------------------- SCHOLARSHIP Select
        StringBuilder scholarQuery = new StringBuilder(" select scholar_ship.id   as id, " +
                "       scholar_ship.name as name, " +
                "       'SCHOLARSHIP' as type, " +
                "       scholar_ship.compressed_photo_id as image_id, " +
                "       scholar_ship.description as description " +
                "from scholar_ship " +
                "where visible is true ");
        if (isQuery) {
            scholarQuery.append(" and lower(scholar_ship.name) like lower(:query) ");
            searchingTableSet.add("SCHOLARSHIP");
        }

        scholarQuery.append(" limit :limit offset :offset ");
        //--------------------------------------------------------------- CONSULTING
        StringBuilder consultingQuery = new StringBuilder(" select consulting.id as id, " +
                "       consulting.name as name, " +
                "       'CONSULTING' as type, " +
                "       consulting.compressed_photo_id as image_id, " +
                "       consulting.about as description " +
                " from consulting " +
                " where visible is true ");
        if (isQuery) {
            consultingQuery.append(" and lower(consulting.name) like lower(:query) ");
            searchingTableSet.add("CONSULTING");
        }
        consultingQuery.append(" limit :limit offset :offset ");
        // ------------------------------------------------------------------------------ UNIVERSITY
        StringBuilder universityQuery = new StringBuilder(" select cast(university.id as varchar) as id, " +
                "       university.name as name, " +
                "       'UNIVERSITY' AS type, " +
                "       university.compressed_photo_id as image_id, " +
                "       university.abbreviation " +
                " from university " +
                " where visible is true  ");
        if (isQuery) {
            universityQuery.append(" and lower(university.name) like lower(:query) ");
            searchingTableSet.add("UNIVERSITY");
        }
        if (dto.getFacultyId() != null) {
            universityQuery.append(" and university.id  in (select university_id from university_faculty where faculty_id = :facultyId) ");
            params.put("facultyId", dto.getFacultyId());
            searchingTableSet.add("UNIVERSITY");
        }
        if (dto.getContinentId() != null) {
            universityQuery.append(" and university.country_id  in ( select country_id from continent_country where continent_id = :continentId) ");
            params.put("continentId", dto.getFacultyId());
            searchingTableSet.add("UNIVERSITY");
        }
        if (dto.getCountryId() != null) {
            universityQuery.append(" and university.country_id = :countryId ");
            params.put("countryId", dto.getFacultyId());
            searchingTableSet.add("UNIVERSITY");
        }
        universityQuery.append(" limit :limit offset :offset ");
        // ----------------------------------------------------------------------------
        // id, name, type, image_id, description
        StringBuilder sqlQuery = new StringBuilder();
        if (dto.getSearchType() == null) {
            StringJoiner joiner = new StringJoiner(" \n union ");
            for (String searchingTable : searchingTableSet) {
                if (searchingTable.equals("SCHOLARSHIP")) {
                    joiner.add("select * from ( " + scholarQuery + " ) ");
//                    sqlQuery.append("select * from (").append(scholarQuery).append(")");
                } else if (searchingTable.equals("CONSULTING")) {
                    joiner.add("select * from ( " + consultingQuery + " ) ");
//                    sqlQuery.append("select * from (").append(consultingQuery).append(")");
                } else if (searchingTable.equals("UNIVERSITY")) {
//                    sqlQuery.append("select * from (").append(universityQuery).append(")");
                    joiner.add("select * from ( " + universityQuery + " ) ");
                }
            }
            return joiner.toString();
           /* sqlQuery = new StringBuilder("""
                          select * from (%s)
                          union
                          select * from (%s)
                          union
                          select * from (%s)
                    """.formatted(scholarQuery, consultingQuery, universityQuery)
            );*/
        } else if (dto.getSearchType().equals("SCHOLARSHIP")) {
            sqlQuery.append(scholarQuery);
        } else if (dto.getSearchType().equals("CONSULTING")) {
            sqlQuery.append(consultingQuery);
        } else if (dto.getSearchType().equals("UNIVERSITY")) {
            sqlQuery.append(universityQuery);
        }
        return sqlQuery.toString();
    }

    private String prepareCountQuery(Boolean isQuery, SearchFilterResDTO dto, Map<String, Object> params) {
        Set<String> searchingTableSet = new LinkedHashSet<>();
        // ----------------------------------------------------------------------- SCHOLARSHIP Count
        StringBuilder scholarQuery = new StringBuilder(" select count(*) from scholar_ship where visible is true ");
        if (isQuery) {
            scholarQuery.append(" and lower(scholar_ship.name) like lower(:query) ");
            searchingTableSet.add("SCHOLARSHIP");
        }
        /*if (dto.getScholarShipId() != null) {
            scholarQuery.append(" and scholar_ship.id =:scholarShipId");
            params.put("scholarShipId", dto.getScholarShipId());
            searchingTableSet.add("SCHOLARSHIP");
        }*/
        /*if (dto.getUniversityId() != null) {
            scholarQuery.append(" and scholar_ship.university_id = :universityId ");
            params.put("universityId", dto.getUniversityId());
            searchingTableSet.add("SCHOLARSHIP");
        }*/
        //--------------------------------------------------------------- CONSULTING Count
        StringBuilder consultingQuery = new StringBuilder("select count(*) from consulting where visible is true ");
        if (isQuery) {
            consultingQuery.append(" and lower(consulting.name) like lower(:query) ");
            searchingTableSet.add("CONSULTING");
        }
       /* if (dto.getUniversityId() != null) {
            consultingQuery.append(" and consulting.id in (select consulting_id from consulting_university where university_id = :universityId) ");
            params.put("universityId", dto.getUniversityId());
            searchingTableSet.add("CONSULTING");
        }*/
        // ------------------------------------------------------------------------------ UNIVERSITY Count
        StringBuilder universityQuery = new StringBuilder(" select count(*) from university where visible is true ");
        if (isQuery) {
            universityQuery.append(" and lower(university.name) like lower(:query) ");
            searchingTableSet.add("UNIVERSITY");
        }
        /*if (dto.getUniversityId() != null) {
            universityQuery.append(" and university.id =:universityId");
            params.put("universityId", dto.getUniversityId());
            searchingTableSet.add("UNIVERSITY");
        }*/
        if (dto.getFacultyId() != null) {
            universityQuery.append(" and university.id  in (select university_id from university_faculty where faculty_id = :facultyId) ");
            params.put("facultyId", dto.getFacultyId());
            searchingTableSet.add("UNIVERSITY");
        }
        if (dto.getContinentId() != null) {
            universityQuery.append(" and university.country_id  in ( select country_id from continent_country where continent_id = :continentId) ");
            params.put("continentId", dto.getFacultyId());
            searchingTableSet.add("UNIVERSITY");
        }
        if (dto.getCountryId() != null) {
            universityQuery.append(" and university.country_id = :countryId ");
            params.put("countryId", dto.getFacultyId());
            searchingTableSet.add("UNIVERSITY");
        }
        // ----------------------------------------------------------------------------
        StringBuilder sqlQuery = new StringBuilder();
        if (dto.getSearchType() == null) {
            StringJoiner joiner = new StringJoiner(" \n union all ");
            for (String searchingTable : searchingTableSet) {
                if (searchingTable.equals("SCHOLARSHIP")) {
                    joiner.add("select * from ( " + scholarQuery +" )");
                } else if (searchingTable.equals("CONSULTING")) {
                    joiner.add("select * from ( " + consultingQuery + " )");
                } else if (searchingTable.equals("UNIVERSITY")) {
                    joiner.add("select * from ( " + universityQuery +" )");
                }
            }
           return joiner.toString();
        } else if (dto.getSearchType().equals("SCHOLARSHIP")) {
            sqlQuery.append(scholarQuery);
        } else if (dto.getSearchType().equals("CONSULTING")) {
            sqlQuery.append(consultingQuery);
        } else if (dto.getSearchType().equals("UNIVERSITY")) {
            sqlQuery.append(universityQuery);
        }
        return sqlQuery.toString();
    }

    private Map<String, List<SearchResponseDTO>> parseToCustomPagination(List<SearchResponseDTO> resultList) {
        return resultList.stream().collect(Collectors.groupingBy(SearchResponseDTO::getType));
    }

}
