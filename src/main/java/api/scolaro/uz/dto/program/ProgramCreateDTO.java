package api.scolaro.uz.dto.program;

import api.scolaro.uz.enums.ProgramType;
import api.scolaro.uz.enums.StudyFormat;
import api.scolaro.uz.enums.StudyMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ProgramCreateDTO {

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

    private LocalDate startDate;

    private LocalDate endDate;

    private Long price;

    private String symbol;

    private Long universityId;

    private Long destinationId;

    private String attachId;

    private Boolean published;

    private StudyFormat studyFormat;

    private StudyMode studyMode;

    private ProgramType type;
}
