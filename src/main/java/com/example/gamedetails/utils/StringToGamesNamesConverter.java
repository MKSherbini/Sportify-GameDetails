package com.example.gamedetails.utils;

import com.example.gamedetails.models.GamesNames;
import org.springframework.core.convert.converter.Converter;

public class StringToGamesNamesConverter implements Converter<String, GamesNames> {
    @Override
    public GamesNames convert(String source) {
        return GamesNames.valueOf(source.toUpperCase());
    }
}
