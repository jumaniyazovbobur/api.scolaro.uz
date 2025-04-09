package api.scolaro.uz.entity;

import api.scolaro.uz.entity.place.DestinationEntity;
import api.scolaro.uz.enums.ProgramType;
import api.scolaro.uz.enums.StudyFormat;
import api.scolaro.uz.enums.StudyMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "program")
public class ProgramEntity extends BaseIdentityEntity {

    @Column(name = "title_uz",length = 100,unique = true)
    private String titleUz;

    @Column(name = "title_ru",length = 100,unique = true)
    private String titleRu;

    @Column(name = "title_en",length = 100,unique = true)
    private String titleEn;

    @Column(name = "description_uz", columnDefinition = "TEXT")
    private String descriptionUz;

    @Column(name = "description_ru", columnDefinition = "TEXT")
    private String descriptionRu;

    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEN;

    @Column(name = "tuition_fees_description_uz")
    private String tuitionFeesDescriptionUz;

    @Column(name = "tuition_fees_description_ru")
    private String tuitionFeesDescriptionRu;

    @Column(name = "tuition_fees_description_en")
    private String tuitionFeesDescriptionEn;

    @Column(name = "scholarship_description_uz")
    private String scholarshipDescriptionUz;

    @Column(name = "scholarship_description_ru")
    private String scholarshipDescriptionRu;

    @Column(name = "scholarship_description_en")
    private String scholarshipDescriptionEn;

    @Column(name = "cost_of_living_description_uz")
    private String costOfLivingDescriptionUz;

    @Column(name = "cost_of_living_description_ru")
    private String costOfLivingDescriptionRu;

    @Column(name = "cost_of_living_description_en")
    private String costOfLivingDescriptionEn;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "price")
    private Long price;

    @Column(name = "symbol",length = 50)
    private String symbol;

    @Column(name = "university_id")
    private Long universityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university; // TODO change to Institute

    @Column(name = "destination_id")
    private Long destinationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", insertable = false, updatable = false)
    private DestinationEntity destination;

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "published")
    private Boolean published = Boolean.FALSE;

    @Column(name = "study_format")
    @Enumerated(EnumType.STRING)
    private StudyFormat studyFormat;

    @Column(name = "study_mode")
    @Enumerated(EnumType.STRING)
    private StudyMode studyMode;

    @Column(name = "program_type")
    @Enumerated(EnumType.STRING)
    private ProgramType programType;

}
