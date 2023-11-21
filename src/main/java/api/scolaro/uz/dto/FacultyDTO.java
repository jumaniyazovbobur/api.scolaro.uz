package api.scolaro.uz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FacultyDTO {
    private String id;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private LocalDateTime createdDate;
}
