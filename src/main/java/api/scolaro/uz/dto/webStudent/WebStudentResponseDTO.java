package api.scolaro.uz.dto.webStudent;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebStudentResponseDTO {
    private String id;
    private String fullName;
    private String about;
    private String aboutUz;
    private String aboutRu;
    private String aboutEn;
    private AttachDTO attachDTO;
    private Integer orderNumber;

}
