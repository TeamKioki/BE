package com.dev.kioki.global.validation.validator;

import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.validation.annotation.PhoneDuplication;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.dev.kioki.global.common.code.status.ErrorStatus.USER_ALREADY_EXIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class PhoneDuplicationValidation implements ConstraintValidator<PhoneDuplication, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(PhoneDuplication constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {

        boolean isAlreadyExist = userRepository.existsByPhone(phone);

        if (!isAlreadyExist) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(USER_ALREADY_EXIST.toString())
                    .addPropertyNode("phone")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
