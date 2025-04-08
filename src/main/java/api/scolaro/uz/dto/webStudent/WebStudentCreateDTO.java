package api.scolaro.uz.dto.webStudent;

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
public class WebStudentCreateDTO {

    @NotBlank(message = "FullName  bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan FullName ning kam uzunligi 3 harfdan iborat bo‘lishi kerak") // Uzbek
    private String fullName;

    @NotBlank(message = "About uz bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3, message = "About uz uzunligi kamida 3 ta belgi bo‘lishi kerak")
    private String aboutUz;

//    @NotBlank(message = "About bo‘sh bo‘lishi mumkin emas")
//    @Size(min = 3, message = "About uzunligi kamida 3 ta belgi bo‘lishi kerak")
    private String aboutRu;

//    @NotBlank(message = "About bo‘sh bo‘lishi mumkin emas")
//    @Size(min = 3, message = "About uzunligi kamida 3 ta belgi bo‘lishi kerak")
    private String aboutEn;

    @NotBlank(message = "Photo id  bo'sh bo'lishi mumkin emas")
    private String photoId;

    @NotNull(message = "Order Number bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Order Number  ning qiymati minimal 1 bo'lsin")
    private Integer orderNumber;

}
