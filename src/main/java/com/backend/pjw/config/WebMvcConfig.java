package com.backend.pjw.config;

import com.backend.pjw.converter.ZonedDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ZonedDateTimeConverter());
        WebMvcConfigurer.super.addFormatters(registry);
    }
}
