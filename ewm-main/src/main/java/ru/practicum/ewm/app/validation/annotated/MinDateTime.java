package ru.practicum.ewm.app.validation.annotated;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = MinDateTimeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MinDateTime {
    String message() default "The date should not be earlier than 2 hours from the present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
