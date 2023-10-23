package api.scolaro.uz.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {

    public AttachDTO() {

    }

    public AttachDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }

    private String id;
    private String originName;
    private Long size;
    private String extension;
    private LocalDateTime createdData;
    private String url;
}
