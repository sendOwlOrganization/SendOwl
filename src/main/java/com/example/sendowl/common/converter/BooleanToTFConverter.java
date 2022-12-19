package com.example.sendowl.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToTFConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean isDelete) {
        return (isDelete != null && isDelete) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String isDelete) {
        return isDelete != null && isDelete.equals("Y");
    }

}
