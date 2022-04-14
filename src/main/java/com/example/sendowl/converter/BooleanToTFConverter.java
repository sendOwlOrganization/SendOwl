package com.example.sendowl.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToTFConverter implements AttributeConverter<Boolean, String> {
    @Override public String convertToDatabaseColumn(Boolean active) {
        return (active != null && active) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String active) {
        return (active != null && active.equals("Y")) ? true : false;
    }

}
