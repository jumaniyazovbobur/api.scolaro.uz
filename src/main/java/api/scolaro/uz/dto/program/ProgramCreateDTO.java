package api.scolaro.uz.dto.program;

import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.enums.ProgramType;
import api.scolaro.uz.enums.StudyFormat;
import api.scolaro.uz.enums.StudyMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProgramCreateDTO {

    private Long id;

    @NotBlank(message = "Title Uz bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3, max = 100, message = "Berilgan Title (Title Uz) ning uzunligi 3 va 100 orasida bo‘lishi kerak") // Uzbek
    private String titleUz;

    @NotBlank(message = "Заголовок Ru не может быть пустым")
    @Size(min = 3, max = 100, message = "Длина указанного Type (Заголовок Ru) должна быть от 3 до 100 символов") // Russian
    private String titleRu;

    @NotBlank(message = "Title En cannot be blank")
    @Size(min = 3, max = 100, message = "The given Type (Title En) length must be between 3 and 100 characters") // English
    private String titleEn;

    @NotBlank(message = "Description bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3, message = "Description uzunligi kamida 3 ta belgi bo‘lishi kerak")
    private String descriptionUz;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 3, message = "Длина описания должна быть не менее 3 символов")
    private String descriptionRu;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 3, message = "Description length must be at least 3 characters")
    private String descriptionEN;

    @NotNull(message = "Start date cannot be blank")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be blank")
    private LocalDate endDate;

    @NotNull(message = " Price bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Price ning qiymati minimal 1 bo'lsin")
    private Long price;

    @NotBlank(message = "Symbol cannot be blank")
    private String symbol;

    @NotNull(message = " University id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "University id ning qiymati minimal 1 bo'lsin")
    private Long universityId;

    @NotNull(message = " Destination id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Destination id ning qiymati minimal 1 bo'lsin")
    private Long destinationId;

    @NotBlank(message = "Attach id  bo'sh bo'lishi mumkin emas")
    private String attachId;

    private Boolean published;

    @NotNull(message = "Study format  bo'sh bo'lishi mumkin emas")
    private StudyFormat studyFormat;

    @NotNull(message = "Study mode  bo'sh bo'lishi mumkin emas")
    private StudyMode studyMode;

    @NotNull(message = "Program type  bo'sh bo'lishi mumkin emas")
    private ProgramType programType;

    @NotNull(message = "Program Requirement Types null bo‘lishi mumkin emas")
    List<ProgramRequirementType> programRequirementTypes;
}
