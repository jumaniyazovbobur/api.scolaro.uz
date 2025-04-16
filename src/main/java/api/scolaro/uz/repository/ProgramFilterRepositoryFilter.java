package api.scolaro.uz.repository;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.country.CountryResponse;
import api.scolaro.uz.dto.destination.DestinationResponse;
import api.scolaro.uz.dto.program.ProgramFilterDTO;
import api.scolaro.uz.dto.program.ProgramResponseFilterDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.enums.*;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.service.AttachService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProgramFilterRepositoryFilter {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AttachService attachService;


    public FilterResultDTO<ProgramResponseFilterDTO> getProgramFilterPage(ProgramFilterDTO programFilterDTO, String language, int page, int size, boolean isAdmin) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(" FROM program p ");

        query.append(" INNER JOIN university u ON p.university_id = u.id ");
        query.append(" INNER JOIN destination d ON p.destination_id = d.id ");
        query.append(" INNER JOIN country c ON u.country_id = c.id ");
        query.append(" LEFT JOIN (")
                .append(" SELECT pr.program_id, STRING_AGG(pr.type, ', ') AS requirements ")
                .append(" FROM program_requirement pr ")
                .append(" GROUP BY pr.program_id ")
                .append(") pr ON pr.program_id = p.id"); // REQUIREMENT BOG‘LASH

        query.append(" WHERE p.visible = true ");

// **Filter qo'shish**
        if (!isAdmin) {
            query.append(" AND p.published = true ");
        }
        if (programFilterDTO.getUniversityId() != null) {
            query.append(" AND u.id = :universityId ");
            params.put("universityId", programFilterDTO.getUniversityId());
        }
        if (programFilterDTO.getDestinationId() != null) {
            query.append(" AND d.id = :destinationId ");
            params.put("destinationId", programFilterDTO.getDestinationId());
        }
        if (programFilterDTO.getCountryId() != null) {
            query.append(" AND c.id = :countryId ");
            params.put("countryId", programFilterDTO.getCountryId());
        }
        if (programFilterDTO.getQuery() != null) {
            query.append(" AND (LOWER(p.title_uz) LIKE :query OR LOWER(p.title_ru) LIKE :query OR LOWER(p.title_en) LIKE :query) ");
            params.put("query", "%" + programFilterDTO.getQuery().toLowerCase() + "%");
        }
        if (programFilterDTO.getProgramType() != null) {
            query.append(" AND p.program_type = :programType ");
            params.put("programType", programFilterDTO.getProgramType());
        }
        if (programFilterDTO.getStudyFormat() != null) {
            query.append(" AND p.study_format = :studyFormat ");
            params.put("studyFormat", programFilterDTO.getStudyFormat());
        }
        if (programFilterDTO.getStudyMode() != null) {
            query.append(" AND p.study_mode = :studyMode ");
            params.put("studyMode", programFilterDTO.getStudyMode());
        }
        if (programFilterDTO.getEndDate() != null) {
            query.append(" AND p.end_date <= :endDate ");
            params.put("endDate", programFilterDTO.getEndDate());
        }
        if (programFilterDTO.getStartDate() != null) {
            query.append(" AND p.start_date >= :startDate ");
            params.put("startDate", programFilterDTO.getStartDate());
        }

        String selectSQL = "SELECT " +
                " p.id AS programId, " +
                " CASE :lang WHEN 'uz' THEN p.title_uz WHEN 'en' THEN p.title_en ELSE p.title_ru END AS programTitle, " +
                " p.published, " +
                " p.start_date, p.end_date, p.price, p.symbol, p.attach_id AS attachProgramId, " +
                " p.study_format, p.study_mode, p.program_type, " +
                " u.id AS universityId, u.name AS universityName, u.logo_id AS attachUniversityLogo, " +
                " c.id AS countryId, " +
                " CASE :lang WHEN 'uz' THEN c.name_uz WHEN 'en' THEN c.name_en ELSE c.name_ru END AS countryName, " +
                " c.attach_id AS attachCountry, " +
                " d.id AS destinationId, " +
                " CASE :lang WHEN 'uz' THEN d.name_uz WHEN 'en' THEN d.name_en ELSE d.name_ru END AS destinationName, " +
                " d.attach_id AS attachDestinationId, d.show_in_main_page AS showInMainPageDestination, " +
                " COALESCE(pr.requirements, '') AS requirements " + query +
                " ORDER BY p.created_date DESC";

        String countSQL = "SELECT COUNT(*) " + query;

        Query selectQuery = entityManager.createNativeQuery(selectSQL);
        Query countQuery = entityManager.createNativeQuery(countSQL);

// **Parametrlarni qo‘shish**
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setParameter("lang", language);

// **Pagination**
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        List<Object[]> resultList = selectQuery.getResultList();
        Long count = ((Number) countQuery.getSingleResult()).longValue();


        // **Natijani DTOga o'tkazish**
        List<ProgramResponseFilterDTO> responseList = resultList.stream().map(this::mapToDTO).collect(Collectors.toList());

        return new FilterResultDTO<>(responseList, count);
    }

    public ProgramResponseFilterDTO getById(Long programId, String language) {
        String sql = "SELECT " +
                " p.id AS programId, " +
                " CASE :lang WHEN 'uz' THEN p.title_uz WHEN 'en' THEN p.title_en ELSE p.title_ru END AS programTitle, " +
                " p.published, " +
                " p.start_date, p.end_date, p.price, p.symbol, p.attach_id AS attachProgramId, " +
                " p.study_format, p.study_mode, p.program_type, " +
                " u.id AS universityId, u.name AS universityName, u.logo_id AS attachUniversityLogo, " +
                " c.id AS countryId, " +
                " CASE :lang WHEN 'uz' THEN c.name_uz WHEN 'en' THEN c.name_en ELSE c.name_ru END AS countryName, " +
                " c.attach_id AS attachCountry, " +
                " d.id AS destinationId, " +
                " CASE :lang WHEN 'uz' THEN d.name_uz WHEN 'en' THEN d.name_en ELSE d.name_ru END AS destinationName, " +
                " d.attach_id AS attachDestinationId, d.show_in_main_page AS showInMainPageDestination, " +
                " COALESCE(pr.requirements, '') AS requirements " +
                " FROM program p " +
                " INNER JOIN university u ON p.university_id = u.id " +
                " INNER JOIN destination d ON p.destination_id = d.id " +
                " INNER JOIN country c ON u.country_id = c.id " +
                " LEFT JOIN ( " +
                "     SELECT pr.program_id, STRING_AGG(pr.type, ', ') AS requirements " +
                "     FROM program_requirement pr " +
                "     WHERE pr.program_id = :programId " +
                "     GROUP BY pr.program_id " +
                " ) pr ON pr.program_id = p.id " +
                " WHERE p.id = :programId"; // Faqat bitta programni olish

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("programId", programId);
        query.setParameter("lang", language);

        Object[] result;
        try {
            result = (Object[]) query.getSingleResult(); // Faqat bitta natija
        } catch (NoResultException e) {
            return null; // Agar hech narsa topilmasa, null qaytariladi
        }

        return mapToDTO(result);
    }


    // {id,title, DestinitionDTO{id,name..}, UniversityDTO, CountiryDTO, AttachDTO{id,url}, Rasm jo'nataman shundagi ma'lumotlar berish kerak.}
    private ProgramResponseFilterDTO mapToDTO(Object[] row) {
        ProgramResponseFilterDTO dto = new ProgramResponseFilterDTO();

        dto.setProgramId((Long) row[0]); // p.id
        dto.setProgramTitle((String) row[1]); // programTitle
        dto.setPublished((Boolean) row[2]); // p.published
        dto.setStartDate((Date) row[3]); // p.start_date
        dto.setEndDate((Date) row[4]); // p.end_date
        dto.setPrice((Long) row[5]); // p.price
        dto.setSymbol((String) row[6]); // p.symbol
        dto.setAttachDTO(attachService.getResponseAttach((String) row[7])); // p.attach_id
        dto.setStudyFormat((String) row[8]); // p.study_format
        dto.setStudyMode((String) row[9]); // p.study_mode
        dto.setProgramType((String) row[10]); // p.program_type

        UniversityResponseDTO universityResponseDTO = new UniversityResponseDTO();
        universityResponseDTO.setId((row[11] != null) ? (Long) row[11] : null);
        universityResponseDTO.setName((String) row[12]);
        universityResponseDTO.setLogo(attachService.getResponseAttach((String) row[13]));
        dto.setUniversity(universityResponseDTO);

        CountryResponse countryResponse = new CountryResponse(
                (row[14] != null) ? (Long) row[14] : null,
                null,
                null,
                null,
                (String) row[15],
                attachService.getResponseAttach((String) row[16]),
                null
        );
        dto.setCountry(countryResponse);


        DestinationResponse destinationResponse = new DestinationResponse(
                (row[17] != null) ? (Long) row[17] : null,
                null,
                null,
                null,
                (String) row[18],
                attachService.getResponseAttach((String) row[19]),
                null,
                null
        );
        dto.setDestination(destinationResponse);

        String requirementsStr = (String) row[21];
        dto.setRequirements(requirementsStr != null && !requirementsStr.isEmpty() ? Arrays.asList(requirementsStr.split(",")) : new ArrayList<>());

        return dto;
    }


}
