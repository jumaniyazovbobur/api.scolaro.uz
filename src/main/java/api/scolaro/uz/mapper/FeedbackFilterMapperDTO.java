package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.profile.ProfileDTO;
import api.scolaro.uz.dto.profile.ProfileResponseFilterDTO;
import api.scolaro.uz.entity.PersonRoleEntity;
import api.scolaro.uz.enums.FeedBackType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackFilterMapperDTO {
    private String id;
    private String content;
    private FeedBackType feedBackType;
    private LocalDateTime createdDate;
    private ProfileDTO person;
}
