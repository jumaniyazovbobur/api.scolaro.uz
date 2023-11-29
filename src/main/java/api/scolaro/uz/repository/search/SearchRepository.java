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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        StringBuilder universityQuery = new StringBuilder("""
                select cast(university.id as varchar) as id,
                university.name as name,'UNIVERSITY' AS type,
                university.photo_id as image_id,university.description,
                (select count(*) from university where visible is true
                and lower(university.name) like lower(concat(:query, '%')) ) as total_count
                from university
                where visible is true and lower(university.name) like lower(concat(:query, '%'))
                """);

        StringBuilder countryQuery = new StringBuilder("""
                select cast(country.id as varchar) id,
                case :lang when 'uz' then country.name_uz
                when 'en' then country.name_en
                else country.name_ru end AS name,
                'COUNTRY' as type, null image_id, null description,
                (select count(*) from country
                where visible is true and case :lang
                when 'uz' then lower(country.name_uz) like lower(concat(:query, '%'))
                when 'en' then lower(country.name_en) like lower(concat(:query, '%'))
                else lower(country.name_ru) like lower(concat(:query, '%'))
                end) as total_count
                from country
                where visible is true and case :lang
                when 'uz' then lower(country.name_uz) like lower(concat(:query, '%'))
                when 'en' then lower(country.name_en) like lower(concat(:query, '%'))
                else lower(country.name_ru) like lower(concat(:query, '%'))
                end
                """);

        StringBuilder consultingQuery = new StringBuilder("""
                select consulting.id as id,
                consulting.name as name, 'CONSULTING' as type,
                consulting.photo_id as image_id, consulting.about as description,
                (select count(*) from consulting where visible is true and lower(consulting.name) like lower(concat(:query, '%'))) as total_count
                from consulting
                where visible is true and lower(consulting.name) like lower(concat(:query, '%'))
                """);

        StringBuilder facultyQuery = new StringBuilder("""
                select faculty.id as id,
                case :lang when 'uz' then faculty.name_uz
                when 'en' then faculty.name_en else faculty.name_ru
                end AS name, 'FACULTY' as type,
                null image_id, null as description,
                (select count(*) from faculty where visible is true and case :lang
                when 'uz' then lower(faculty.name_uz) like lower(concat(:query, '%'))
                when 'en' then lower(faculty.name_en) like lower(concat(:query, '%'))
                else lower(faculty.name_ru) like lower(concat(:query, '%')) end) as total_count
                from faculty
                where visible is true and case :lang
                when 'uz' then lower(faculty.name_uz) like lower(concat(:query, '%'))
                when 'en' then lower(faculty.name_en) like lower(concat(:query, '%'))
                else lower(faculty.name_ru) like lower(concat(:query, '%')) end
                """);

        StringBuilder scholarQuery = new StringBuilder("""
                select scholar.id   as id,
                scholar.name as name, 'SCHOLAR' as type,
                scholar.photo_id as image_id, scholar.description as description,
                (select count(*) from scholar_ship as scholar
                where visible is true and lower(scholar.name) like lower(concat(:query, '%'))) as total_count
                from scholar_ship as scholar
                where visible is true and lower(scholar.name) like lower(concat(:query, '%'))
                """);

        Map<String, Object> params = new HashMap<>();
        params.put("lang", lang);
        boolean isQuery = false;
        if (Optional.ofNullable(dto).isPresent()) {
            isQuery = Optional.ofNullable(dto.getQuery()).isPresent();
            params.put("query", Optional.ofNullable(dto.getQuery()).isPresent() ? dto.getQuery() : "");

            if (Optional.ofNullable(dto.getCountryId()).isPresent()) {
                // university in the country
                universityQuery.append(" and university.country_id = :countryId");
                countryQuery.append(" and false");
                consultingQuery.append(" and false");
                facultyQuery.append(" and false");
                scholarQuery.append(" and false");
                params.put("countryId", dto.getCountryId());
            }
            if (Optional.ofNullable(dto.getConsultingId()).isPresent()) {
                consultingQuery.append(" and consulting.id = :consultingId");
                countryQuery.append(" and false");
                universityQuery.append(" and false");
                facultyQuery.append(" and false");
                scholarQuery.append(" and false");
                params.put("consultingId", dto.getConsultingId());
            }
            if (Optional.ofNullable(dto.getFacultyId()).isPresent()) {
                // getting university which university's faculty like
                universityQuery.append(" and university.id in (select uf.university_id from university_faculty as uf where uf.faculty_id = :facultyId)");
                countryQuery.append(" and false");
                consultingQuery.append(" and false");
                facultyQuery.append(" and false");
                scholarQuery.append(" and false");
                params.put("facultyId", dto.getFacultyId());
            }
            if (Optional.ofNullable(dto.getUniversityId()).isPresent()) {
                universityQuery.append(" and university.id = :universityId");
                countryQuery.append(" and false");
                consultingQuery.append(" and false");
                facultyQuery.append(" and false");
                scholarQuery.append(" and false");
                params.put("universityId", dto.getUniversityId());
            }
            if (Optional.ofNullable(dto.getScholarShipId()).isPresent()) {
                scholarQuery.append(" and scholar.id = :scholarId");
                countryQuery.append(" and false");
                consultingQuery.append(" and false");
                facultyQuery.append(" and false");
                universityQuery.append(" and false");
                params.put("scholarId", dto.getScholarShipId());
            }
            if (Optional.ofNullable(dto.getContinentId()).isPresent()) {
                universityQuery.append(" and university.country_id in (select cc.country_id from continent_country cc where cc.continent_id = :continentId)");
                countryQuery.append(" and false");
                consultingQuery.append(" and false");
                facultyQuery.append(" and false");
                scholarQuery.append(" and false");
                params.put("continentId", dto.getContinentId());
            }
        }

        StringBuilder sqlQuery = new StringBuilder("""
                SELECT id, name, type, image_id, description, total_count
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
        Long totalCount = (Long) countQuery.getSingleResult();


        List<SearchResponseDTO> resultList = apartmentList
                .stream()
                .map(this::convert)
                .toList();

        CustomPaginationForSearch res = new CustomPaginationForSearch();
        res.setTotalCount(totalCount);
        parseToCustomPagination(resultList)
                .forEach((string, searchResponseDTOS) -> {
                    switch (string) {
                        case "FACULTY" -> res.setFaculty(searchResponseDTOS);
                        case "COUNTRY" -> res.setCountry(searchResponseDTOS);
                        case "SCHOLAR" -> {
                            long total = searchResponseDTOS.stream().map(SearchResponseDTO::getTempCount).findFirst().orElse(0L);
                            res.setScholar(new CustomPaginationDTO<>(total, searchResponseDTOS));
                        }
                        case "UNIVERSITY" -> {
                            long total = searchResponseDTOS.stream().map(SearchResponseDTO::getTempCount).findFirst().orElse(0L);
                            res.setUniversity(new CustomPaginationDTO<>(total, searchResponseDTOS));
                        }
                        case "CONSULTING" -> {
                            long total = searchResponseDTOS.stream().map(SearchResponseDTO::getTempCount).findFirst().orElse(0L);
                            res.setConsulting(new CustomPaginationDTO<>(total, searchResponseDTOS));
                        }
                    }
                });
        return res;
    }

    private SearchResponseDTO convert(Object[] object) {
        String imageId = MapperUtil.getStringValue(object[3]);
        String description = MapperUtil.getStringValue(object[4]);
        return new SearchResponseDTO(
                MapperUtil.getStringValue(object[0]),
                MapperUtil.getStringValue(object[1]),
                MapperUtil.getStringValue(object[2]),
                imageId == null ? null : attachService.getUrl(imageId),
                description,
                MapperUtil.getLongValue(object[5])
        );
    }


    private Map<String, List<SearchResponseDTO>> parseToCustomPagination(List<SearchResponseDTO> resultList) {
        return resultList.stream().collect(Collectors.groupingBy(SearchResponseDTO::getType));
    }

}
