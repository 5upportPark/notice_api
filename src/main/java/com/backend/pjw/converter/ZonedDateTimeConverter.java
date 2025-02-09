package com.backend.pjw.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(String str) {
        return ZonedDateTime.of (LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.systemDefault());
    }
}
