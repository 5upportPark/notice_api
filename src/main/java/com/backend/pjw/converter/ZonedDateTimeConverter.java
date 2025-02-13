package com.backend.pjw.converter;

import io.micrometer.common.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(String str) {
        if(StringUtils.isEmpty(str)) throw new IllegalArgumentException("날짜 포맷이 잘못되었습니다.");
        return ZonedDateTime.of(LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), ZoneId.systemDefault());
    }
}
