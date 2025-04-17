package api.scolaro.uz.dto.institute;

import api.scolaro.uz.dto.instituteAttach.InstituteAttachResponseDTO;
import api.scolaro.uz.dto.instituteDestination.InstituteDestinationResponseDTO;
import api.scolaro.uz.enums.GeneralStatus;
import lombok.Data;

import java.util.List;

@Data
public class InstituteResponseUserDTO {

    private String name;
    private Long countryId;
    private String cityName;
    private Long rating;
    private String about;
    private String territory;
    private String education;
    private List<InstituteAttachResponseDTO> instituteAttachResponseDTOS;
    private List<InstituteDestinationResponseDTO> instituteDestinationResponseDTOS;
    private String phoneNumber1;
    private String phoneNumber2;
    private String webSite;
    private String instagramUrl;
    private String facebookUrl;
    private String email;
    private String photoId;
    private String compressedPhotoId;
    private String logoId;
    private String compressedLogoId;
    private Boolean masterDegree;
    private Boolean doctoralDegree;
    private GeneralStatus status;

}
