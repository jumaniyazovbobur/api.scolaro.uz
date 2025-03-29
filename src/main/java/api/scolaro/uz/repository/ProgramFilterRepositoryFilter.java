package api.scolaro.uz.repository;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.program.ProgramFilterDTO;
import api.scolaro.uz.dto.program.ProgramResponseFilterDTO;
import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.enums.*;
import api.scolaro.uz.exp.AppBadRequestException;
import jakarta.persistence.EntityManager;
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


    public FilterResultDTO<ProgramResponseFilterDTO> getProgramFilterPage(ProgramFilterDTO programFilterDTO, String language, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(" FROM program p ");

        query.append(" INNER JOIN university u ON p.university_id = u.id ");
        query.append(" INNER JOIN destination d ON p.destination_id = d.id ");
        query.append(" INNER JOIN country c ON u.country_id = c.id ");
        query.append(" LEFT JOIN program_requirement pr ON pr.program_id = p.id "); // REQUIREMENT BOG‘LASH

        query.append(" WHERE p.visible = true ");

        // **Filter qo'shish**
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
        if (programFilterDTO.getTitleUz() != null) {
            query.append(" AND p.title_uz = :titleUz");
            params.put("titleUz", programFilterDTO.getTitleUz());
        }
        if (programFilterDTO.getTitleRu() != null) {
            query.append(" AND p.title_ru = :titleRu");
            params.put("titleRu", programFilterDTO.getTitleRu());
        }
        if (programFilterDTO.getTitleEn() != null) {
            query.append(" AND p.title_en = :titleEn");
            params.put("titleEn", programFilterDTO.getTitleEn());
        }
        if (programFilterDTO.getProgramType() != null) {
            query.append(" AND p.program_type = :programType");
            params.put("programType", programFilterDTO.getProgramType());
        }
        if (programFilterDTO.getStudyFormat() != null) {
            query.append(" AND p.study_format = :studyFormat");
            params.put("studyFormat", programFilterDTO.getStudyFormat());
        }
        if (programFilterDTO.getStudyMode() != null) {
            query.append(" AND p.study_mode = :studyMode");
            params.put("studyMode", programFilterDTO.getStudyMode());
        }
        if (programFilterDTO.getEndDate() != null) {
            query.append(" AND p.end_date <= :endDate");
            params.put("endDate", programFilterDTO.getEndDate());
        }

        // **Til bo‘yicha maydonlarni tanlash**
        String programTitle, countryName, destinationName;
        if ("uz".equals(language)) {
            programTitle = "p.title_uz AS programTitle";
            countryName = "c.name_uz AS countryName";
            destinationName = "d.name_uz AS destinationName";
        } else if ("ru".equals(language)) {
            programTitle = "p.title_ru AS programTitle";
            countryName = "c.name_ru AS countryName";
            destinationName = "d.name_ru AS destinationName";
        } else {
            programTitle = "p.title_en AS programTitle";
            countryName = "c.name_en AS countryName";
            destinationName = "d.name_en AS destinationName";
        }

        // **REQUIREMENT LISTNI OLISH** (PostgreSQL uchun)
        String requirementsField = " COALESCE(STRING_AGG(pr.type, ','), '') AS requirements ";

// **Admin bo‘lsa, published maydonini olish**
        boolean isAdmin = EntityDetails.hasRoleCurrentUser(RoleEnum.ROLE_ADMIN);

        String selectSQL = "SELECT " +
                " p.id AS programId, " +
                programTitle + ", " +
                (isAdmin ? " p.published, " : "") + // **Admin bo‘lsa qo‘shamiz, bo‘lmasa chiqarib tashlaymiz**
                " p.start_date, p.end_date, p.price, p.symbol, p.attach_id AS attachProgramId, " +
                " p.study_format, p.study_mode, p.program_type, " +
                " u.id AS universityId, u.name AS universityName, u.logo_id AS attachUniversityLogo, " +
                " c.id AS countryId, " +
                countryName + ", " +
                " c.attach_id AS attachCountry, " +
                " d.id AS destinationId, " +
                destinationName + ", " +
                " d.attach_id AS attachDestinationId, d.show_in_main_page AS showInMainPageDestination, " +
                requirementsField +
                query +
                " GROUP BY p.id, u.id, d.id, c.id ";


        // **COUNT query**
        String countSQL = "SELECT COUNT(DISTINCT p.id) " + query; // DISTINCT qo‘shildi

        Query selectQuery = entityManager.createNativeQuery(selectSQL);
        Query countQuery = entityManager.createNativeQuery(countSQL);

        // **Parametrlarni qo‘shish**
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

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
        // **Til bo‘yicha maydonlarni tanlash**
        String programTitle, countryName, destinationName;
        if ("uz".equals(language)) {
            programTitle = "p.title_uz AS programTitle";
            countryName = "c.name_uz AS countryName";
            destinationName = "d.name_uz AS destinationName";
        } else if ("ru".equals(language)) {
            programTitle = "p.title_ru AS programTitle";
            countryName = "c.name_ru AS countryName";
            destinationName = "d.name_ru AS destinationName";
        } else {
            programTitle = "p.title_en AS programTitle";
            countryName = "c.name_en AS countryName";
            destinationName = "d.name_en AS destinationName";
        }

        // **To‘liq Native Query**
        boolean isAdmin = EntityDetails.hasRoleCurrentUser(RoleEnum.ROLE_ADMIN);

        String sql = "SELECT " +
                " p.id AS programId, " +
                programTitle + ", " +
                (isAdmin ? " p.published, " : "") +  // **Admin bo‘lsa qo‘shamiz, bo‘lmasa chiqaramiz**
                " p.start_date, p.end_date, p.price, p.symbol, p.attach_id AS attachProgramId, " +
                " p.study_format, p.study_mode, p.program_type, " +
                " u.id AS universityId, u.name AS universityName, u.logo_id AS attachUniversityLogo, " +
                " c.id AS countryId, " +
                countryName + ", " +
                " c.attach_id AS attachCountry, " +
                " d.id AS destinationId, " +
                destinationName + ", " +
                " d.attach_id AS attachDestinationId, d.show_in_main_page AS showInMainPageDestination, " +
                " COALESCE(STRING_AGG(pr.type, ','), '') AS requirements " + // **Requirementsni qo‘shish**
                " FROM program p " +
                " INNER JOIN university u ON p.university_id = u.id " +
                " INNER JOIN destination d ON p.destination_id = d.id " +
                " INNER JOIN country c ON u.country_id = c.id " +
                " LEFT JOIN program_requirement pr ON pr.program_id = p.id " +
                " WHERE p.id = :programId AND p.visible = true " +
                " GROUP BY p.id, u.id, d.id, c.id ";


        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("programId", programId);

        List<Object[]> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }

        return mapToDTO(resultList.get(0));
    }


    private ProgramResponseFilterDTO mapToDTO(Object[] row) {
        ProgramResponseFilterDTO dto = new ProgramResponseFilterDTO();

        dto.setProgramId((Long) row[0]); // p.id
        dto.setProgramTitle((String) row[1]); // programTitle
        dto.setPublished((Boolean) row[2]); // p.published
        dto.setStartDate((Date) row[3]); // p.start_date
        dto.setEndDate((Date) row[4]); // p.end_date
        dto.setPrice((Long) row[5]); // p.price
        dto.setSymbol((String) row[6]); // p.symbol
        dto.setAttachProgramId((String) row[7]); // p.attach_id
        dto.setStudyFormat((String) row[8]); // p.study_format
        dto.setStudyMode((String) row[9]); // p.study_mode
        dto.setProgramType((String) row[10]); // p.program_type

        dto.setUniversityId((row[11] != null) ? (Long) row[11] : null); // u.id
        dto.setUniversityName((String) row[12]); // u.name
        dto.setAttachUniversityLogo((String) row[13]); // u.logo_id

        dto.setCountryId((row[14] != null) ? (Long) row[14] : null); // c.id
        dto.setCountryName((String) row[15]); // countryName (til bo‘yicha)
        dto.setAttachCountry((String) row[16]); // c.attach_id

        dto.setDestinationId((row[17] != null) ? (Long) row[17] : null); // d.id
        dto.setDestinationName((String) row[18]); // destinationName (til bo‘yicha)
        dto.setAttachDestinationId((String) row[19]); // d.attach_id
        dto.setShowInMainPageDestination((row[20] != null) ? (Boolean) row[20] : null); // d.show_in_main_page

        // **Requirements stringini Listga aylantirish**
        String requirementsStr = (String) row[21]; // `STRING_AGG` natijasi
        dto.setRequirements(requirementsStr != null && !requirementsStr.isEmpty() ? Arrays.asList(requirementsStr.split(",")) : new ArrayList<>());

        return dto;
    }



}
