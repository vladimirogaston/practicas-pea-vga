package ar.vga.com.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainObjectsRegistry {
    private static Map<Class<?>, Map<Long, DomainObject>> registry = new HashMap<>();
    public static Map<Long, DomainObject> getDomainObjectsRegistry(Class<?> clazz) {
        return registry.get(clazz);
    }
}
