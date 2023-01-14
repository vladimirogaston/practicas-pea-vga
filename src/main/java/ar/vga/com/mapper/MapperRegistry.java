package ar.vga.com.mapper;

import ar.vga.com.domain.Person;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
    private static Map<Class<?>, AbstractMapper> mappers;
    private static MapperRegistry mapperRegistry;

    private MapperRegistry(){
        mappers = new HashMap<>();
        mappers.put(Person.class, new PersonMapper());
    }

    public static MapperRegistry getInstance() {
        if(mapperRegistry == null) {
            mapperRegistry = new MapperRegistry();
            return mapperRegistry;
        }
        return mapperRegistry;
    }

    public AbstractMapper getMapper(Class<?> clazz){
        return mappers.get(clazz);
    }
}