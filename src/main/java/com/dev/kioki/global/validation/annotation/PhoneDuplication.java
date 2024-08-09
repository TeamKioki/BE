package com.dev.kioki.global.validation.annotation;

import com.dev.kioki.global.validation.validator.PhoneDuplicationValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneDuplicationValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneDuplication {
    String message() default "이미 가입된 휴대폰 번호 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}