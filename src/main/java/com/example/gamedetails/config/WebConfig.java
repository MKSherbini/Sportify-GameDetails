package com.example.gamedetails.config;

import com.example.gamedetails.utils.StringToGamesNamesConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToGamesNamesConverter());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}