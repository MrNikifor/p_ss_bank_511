package com.bank.account.validator;

import com.bank.account.dto.AccountDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidatorDTO {
    private final Validator validator;

    public void validate(AccountDetailsDTO accountDetailsDTO) {
        Set<ConstraintViolation<AccountDetailsDTO>> constraintViolations = validator.validate(accountDetailsDTO);
        if (!constraintViolations.isEmpty()) {
            String resultViolation = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((s1, s2) -> s1 + ". " + s2).orElse("");
            log.error("Данные не валидны, ошибки валидации: {}", resultViolation);
            throw new ValidationException(resultViolation);
        }
    }
}
