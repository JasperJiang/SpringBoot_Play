package com.example.demo.validator.constraintvalidators;

import com.example.demo.validator.constraint.IsValidatedName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsValidatedNameValidator implements ConstraintValidator<IsValidatedName, String> {

    @Autowired
    MessageSource messageSource;

    @Override
    public void initialize(IsValidatedName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String pattern = "[^a-zA-Z]*";
        if (Pattern.matches(pattern, value)){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(getMessage("error.name.atLeastOneAlphabet")).addConstraintViolation();
            return false;
        }
        pattern = "[0-9a-zA-Z@&_-]+";
        if (!Pattern.matches(pattern, value)){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(getMessage("error.name.specialCharacters")).addConstraintViolation();
            return false;
        }
        return true;
    }

    public String getMessage(String val,Object... o){
        return messageSource.getMessage(val,o,null);
    }
}
