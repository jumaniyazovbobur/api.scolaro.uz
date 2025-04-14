package api.scolaro.uz.dto.instituteAttach;

import api.scolaro.uz.enums.InstituteAttachType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InstituteAttachValidator implements ConstraintValidator<ValidInstituteAttach, InstituteAttachRequestDTO> {
    @Override
    public boolean isValid(InstituteAttachRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getInstituteAttachType() == null) {
            return true; // handled by other validations
        }

        boolean isValid = true;

        if (dto.getInstituteAttachType() == InstituteAttachType.PHOTO) {
            isValid = dto.getAttachId() != null && !dto.getAttachId().trim().isEmpty();

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("attachId is required when type is PHOTO")
                        .addPropertyNode("attachId")
                        .addConstraintViolation();
            }

        } else if (dto.getInstituteAttachType() == InstituteAttachType.VIDEO) {
            isValid = dto.getVideoLink() != null && !dto.getVideoLink().trim().isEmpty();

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("videoLink is required when type is VIDEO")
                        .addPropertyNode("videoLink")
                        .addConstraintViolation();
            }
        }

        return isValid;
    }
}
