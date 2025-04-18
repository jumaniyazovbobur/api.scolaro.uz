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

    @Column(name = "title_uz", length = 100, unique = true)
    private String titleUz;
    @Column(name = "title_ru", length = 100, unique = true)
    private String titleRu;
    @Column(name = "title_en", length = 100, unique = true)
    private String titleEn;


    @Column(name = "program_type")
    @Enumerated(EnumType.STRING)
    private ProgramType programType; // Тип

    @Column(name = "start_date")
    private LocalDate startDate; // Последний срок start
    @Column(name = "end_date")
    private LocalDate endDate; // Последний срок end

    @Column(name = "university_id")
    private Long universityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university; // TODO change to Institute

    @Column(name = "destination_id")
    private Long destinationId; // направление
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", insertable = false, updatable = false)
    private DestinationEntity destination;
    @Column(name = "study_duration")
    private Integer studyDuration; // Продолжительность
    @Column(name = "mainSubject")
    private String mainSubject; // Основной предмет

    // If scholar Ship -> грант
    @Column(name = "price")
    private Long price; // Стоимость гранта
    @Column(name = "symbol", length = 50)
    private String symbol;
    @Column(name = "study_format")
    @Enumerated(EnumType.STRING)
    private StudyFormat studyFormat; // Формат обучения
    @Column(name = "study_mode")
    @Enumerated(EnumType.STRING)
    private StudyMode studyMode; // Режим обучения

    @Column(name = "tuition_fees_description_uz")
    private String tuitionFeesDescriptionUz;  // Стоимость обучения uz
    @Column(name = "tuition_fees_description_ru")
    private String tuitionFeesDescriptionRu; // Стоимость обучения
    @Column(name = "tuition_fees_description_en")
    private String tuitionFeesDescriptionEn; // Стоимость обучения
    @Column(name = "scholarship_description_uz")
    private String scholarshipDescriptionUz; // Стипендия
    @Column(name = "scholarship_description_ru")
    private String scholarshipDescriptionRu; // Стипендия
    @Column(name = "scholarship_description_en")
    private String scholarshipDescriptionEn; // Стипендия
    @Column(name = "cost_of_living_description_uz")
    private String costOfLivingDescriptionUz; // Стоимость проживания
    @Column(name = "cost_of_living_description_ru")
    private String costOfLivingDescriptionRu;  // Стоимость проживания
    @Column(name = "cost_of_living_description_en")
    private String costOfLivingDescriptionEn;  // Стоимость проживания

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "description_uz", columnDefinition = "TEXT")
    private String descriptionUz;
    @Column(name = "description_ru", columnDefinition = "TEXT")
    private String descriptionRu;
    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEN;

    @Column(name = "published")
    private Boolean published = Boolean.FALSE;
}
