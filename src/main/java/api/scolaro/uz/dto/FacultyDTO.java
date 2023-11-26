package api.scolaro.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacultyDTO {
    private String id;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private LocalDateTime createdDate;
    private int orderNumber;
    private String subFaculty;
    private Integer universityCount;
}
