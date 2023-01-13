package ar.vga.com.mapper;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
    private static Map<Class<?>, AbstractMapper> mappers =  new HashMap<>();
    public static AbstractMapper get(Class<?> clazz) {
        return mappers.get(clazz);
    }
}