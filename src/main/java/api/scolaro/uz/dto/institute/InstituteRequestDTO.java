package api.scolaro.uz.dto.institute;

import api.scolaro.uz.dto.instituteAttach.InstituteAttachRequestDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationRequestDTO;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class InstituteRequestDTO {

    @NotBlank(message = "Institute name bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Institute name ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String name;

    @NotNull(message = "Country id bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Country id ning qiymati minimal 1 bo'lsin")
    private Long countryId;

    @NotBlank(message = "City name bo‘sh bo‘lishi mumkin emas")
    @Size(min = 2,  message = "Berilgan city name  ning uzunligi 2 harifdan iborat bo‘lishi kerak")
    private String cityName;

    @NotNull(message = "Rating  bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Ratingning qiymati minimal 1 bo'lsin")
    private Long rating;

    // Об университете
    @NotBlank(message = "About Uz Institute bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan About Uz Institute  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String aboutUz;
    private String aboutEn;
    private String aboutRu;

    // abbreviation ?

    // Территория
    @NotBlank(message = "Territory Uz bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Territory Uz  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String territoryUz;
    private String territoryEn;
    private String territoryRu;

    // Обучение
    @NotBlank(message = "Education Uz bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Education Uz  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String educationUz;
    private String educationEn;
    private String educationRu;

    // Программы  Shu universitetga bo'glangan programmalarni programmani ichida ko'rsatiladi. Bunda emas

    // Медия  photo+video(link)   InstituteAttachEntity - merge method yozish kerak
    // List<InstituteAttachEntity> ...

    @NotNull(message = "Institute Attach Request DTO null bo‘lishi mumkin emas")
    List<InstituteAttachRequestDTO> instituteAttachRequestDTOS;

    // Направления -> InstituteDestinationEntity  merge method yozish kerak.
    @NotNull(message = "Institute Destination Request DTO null bo‘lishi mumkin emas")
    List<InstituteDestinationRequestDTO> instituteDestinationRequestDTOS;

    // Контакты
    @NotBlank(message = "Phone Number  bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Phone Number  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String phoneNumber1;
    private String phoneNumber2;

    @NotBlank(message = "Website  bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Website  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String webSite;

    @NotBlank(message = "Instagram Url  bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Instagram Url  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String instagramUrl;

    @NotBlank(message = "Facebook Url  bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan Facebook Url  ning uzunligi 3 harifdan iborat bo‘lishi kerak")
    private String facebookUrl;

    @Email
    @Size(min = 3, max = 100, message = "Berilgan email uzunligi 3 va 100 orasida bo'lishi kerak")
    @NotBlank(message = "Email bo'sh bo'lishi mumkin emas")
    private String email;

    @NotBlank(message = "Photo id  bo'sh bo'lishi mumkin emas")
    private String photoId;
    private String compressedPhotoId;

    @NotBlank(message = "Logo id  bo'sh bo'lishi mumkin emas")
    private String logoId;
    private String compressedLogoId;

    private Boolean masterDegree;

    private Boolean doctoralDegree;

    @NotNull(message = "General status  bo'sh bo'lishi mumkin emas")
    private GeneralStatus status;

}
