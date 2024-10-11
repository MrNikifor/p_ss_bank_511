package com.bank.profile.test;

import com.bank.profile.entity.Registration;
import com.bank.profile.validators.EntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static liquibase.util.Validate.fail;

public class EntityValidatorTest {

    private EntityValidator<Registration> registrationEntityValidator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            registrationEntityValidator = new EntityValidator<>(validator);
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void testValidate() {

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setCountry("Moscow");

        registrationEntityValidator.validate(registration);

        registration = new Registration();
        registration.setId(null);
        registration.setCountry(new String(new char[167]));

        try {
            registrationEntityValidator.validate(registration);
            fail("Ожидалось исключение валидатора, но валидатор не сработал");
        } catch (ValidationException e) {
        }
    }
}
