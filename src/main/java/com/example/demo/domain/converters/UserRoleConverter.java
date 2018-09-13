package com.example.demo.domain.converters;

import com.example.demo.enums.UserRoleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(UserRoleEnum userRoleEnum) {
        return userRoleEnum.name();
    }

    @Override
    public UserRoleEnum convertToEntityAttribute(String s) {
        return UserRoleEnum.valueOf(s);
    }
}
