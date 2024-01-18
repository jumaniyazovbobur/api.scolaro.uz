package api.scolaro.uz.dto.consultingProfile;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author 'Mukhtarov Sarvarbek' on 16.01.2024
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultingProfileInfoAsAdminDTO {
    String id;
    String name;
    String surname;
    String phone;
    AttachDTO photo;

    public static ConsultingProfileInfoAsAdminDTO toDTO(ConsultingProfileEntity entity, AttachDTO photo) {
        return new ConsultingProfileInfoAsAdminDTO(entity.getId(), entity.getName(), entity.getSurname(), entity.getPhone(), photo);
    }
}
