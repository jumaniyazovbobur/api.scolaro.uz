package api.scolaro.uz.repository.search;

import api.scolaro.uz.dto.search.SearchResponseDTO;
import api.scolaro.uz.util.MapperUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public CustomPaginationForSearch search(String query, String lang, int page, int size) {

        StringBuilder universityQuery = new StringBuilder("""
                select cast(univer.id as varchar) as id,
                univer.name as name,'UNIVERSITY' AS type
                from university as univer
                where visible is true and lower(univer.name) like lower(concat(:query, '%'))
                """);

        StringBuilder countryQuery = new StringBuilder("""
                select cast(country.id as varchar) id,
                case :lang when 'uz' then country.name_uz
                when 'en' then country.name_en
                else country.name_ru end AS name,
                'COUNTRY' as type
                from country
                where visible is true and case :lang
                when 'uz' then lower(country.name_uz) like lower(concat(:query, '%'))
                when 'en' then lower(country.name_en) like lower(concat(:query, '%'))
                else lower(country.name_ru) like lower(concat(:query, '%'))
                end
                """);

        StringBuilder consultingQuery = new StringBuilder("""
                select consulting.id as id,
                consulting.name as name, 'CONSULTING' as type
                from consulting
                where visible is true and lower(consulting.name) like lower(concat(:query, '%'))
                """);

        StringBuilder facultyQuery = new StringBuilder("""
                select faculty.id as id,
                case :lang when 'uz' then faculty.name_uz
                when 'en' then faculty.name_en else faculty.name_ru
                end AS name, 'FACULTY' as type
                from faculty
                where visible is true and case :lang
                when 'uz' then lower(faculty.name_uz) like lower(concat(:query, '%'))
                when 'en' then lower(faculty.name_en) like lower(concat(:query, '%'))
                else lower(faculty.name_ru) like lower(concat(:query, '%')) end
                """);

        StringBuilder scholarQuery = new StringBuilder("""
                select scholar.id   as id,
                scholar.name as name, 'SCHOLAR' as type
                from scholar_ship as scholar
                where visible is true and lower(scholar.name) like lower(concat(:query, '%'))
                """);

        Map<String, Object> params = new HashMap<>();
        params.put("lang", lang);
        params.put("query", query);

        StringBuilder sqlQuery = new StringBuilder("""
                SELECT id, name, type
                FROM (
                %s
                union
                %s
                union
                %s
                union
                %s
                union
                %s ) AS result limit %d offset %d
                """.formatted(scholarQuery, facultyQuery, consultingQuery, countryQuery, universityQuery, size, page * size)
        );
        String countSqlQuery = """
                SELECT count(*)
                FROM (
                %s
                union
                %s
                union
                %s
                union
                %s
                union
                %s ) AS result
                """.formatted(scholarQuery, facultyQuery, consultingQuery, countryQuery, universityQuery);

        Query selectQuery = entityManager.createNativeQuery(sqlQuery.toString());
        Query countQuery = entityManager.createNativeQuery(countSqlQuery);

        params.forEach(selectQuery::setParameter);
        params.forEach(countQuery::setParameter);

        List<Object[]> apartmentList = selectQuery.getResultList();
        List<SearchResponseDTO> resultList = apartmentList.stream().map(this::convert).toList();
        Long totalCount = (Long) countQuery.getSingleResult();
        CustomPaginationForSearch res = new CustomPaginationForSearch();
        res.setTotalCount(totalCount);

        return parseToCustomPagination(resultList, totalCount);
    }

    private SearchResponseDTO convert(Object[] object) {
        return new SearchResponseDTO(MapperUtil.getStringValue(object[0]), MapperUtil.getStringValue(object[1]), MapperUtil.getStringValue(object[2]));
    }

    private CustomPaginationForSearch parseToCustomPagination(List<SearchResponseDTO> resultList, Long totalCount) {
        Map<String, List<SearchResponseDTO>> collect = resultList.stream().collect(Collectors.groupingBy(SearchResponseDTO::getType));
        CustomPaginationForSearch res = new CustomPaginationForSearch();
        res.setTotalCount(totalCount);
        collect.forEach((string, searchResponseDTOS) -> {
            switch (string) {
                case "FACULTY" -> res.setFaculty(searchResponseDTOS);
                case "COUNTRY" -> res.setCountry(searchResponseDTOS);
                case "SCHOLAR" -> res.setScholar(searchResponseDTOS);
                case "CONSULTING" -> res.setConsulting(searchResponseDTOS);
                case "UNIVERSITY" -> res.setUniversity(searchResponseDTOS);
            }
        });
        return res;
    }
}
