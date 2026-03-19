package com.hotel.booking.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckInBeforeCheckOutValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckInBeforeCheckOut {
    String message() default "checkIn must be before checkOut";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
