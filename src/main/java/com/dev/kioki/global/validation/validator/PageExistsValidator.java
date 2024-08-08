package com.dev.kioki.global.validation.validator;

import com.dev.kioki.global.common.code.status.ErrorStatus;
import com.dev.kioki.global.validation.annotation.ExistPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageExistsValidator implements ConstraintValidator<ExistPage, Integer> {
    @Override
    public void initialize(ExistPage constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context){
        if(value > 0)
            return true;
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PAGE_NOT_VALID.toString()).addConstraintViolation();
            return false;
        }
    }
}
