package api.scolaro.uz.dto.instituteAttach;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InstituteAttachValidator.class)
@Documented
public  @interface ValidInstituteAttach {
    String message() default "Invalid attach information for given InstituteAttachType";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
