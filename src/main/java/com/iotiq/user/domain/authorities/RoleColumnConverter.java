package com.iotiq.user.domain.authorities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class RoleColumnConverter implements AttributeConverter<Role, String> {

    private final List<RoleConverter> converters;

    public RoleColumnConverter(List<RoleConverter> converters) {
        this.converters = converters;
    }

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role != null) {
            return role.name();
        }
        return null;
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        if (s == null)
            return null;
        for (RoleConverter converter : converters) {
            Role convert = converter.convert(s);

            if (convert != null) {
                return convert;
            }
        }
        return null;
    }
}
