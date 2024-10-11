package com.bank.profile.validators;

import com.bank.profile.entity.abstracts.AbstractEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityValidator<ENTITY extends AbstractEntity> {

    private final Validator validator;

    public void validate(ENTITY entity) throws ValidationException {
        Set<ConstraintViolation<ENTITY>> constraintViolations = validator.validate(entity);
        if (!constraintViolations.isEmpty()) {
            String resultViolation = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((s1, s2) -> s1 + ". " + s2).orElse("");
            log.error("Данные не валидны, ошибки валидации: {}", resultViolation);
            throw new ValidationException(resultViolation);
        }
    }

}
