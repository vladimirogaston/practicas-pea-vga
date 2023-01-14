package ar.vga.com.mapper;

import ar.vga.com.domain.DomainObject;
import ar.vga.com.domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapperRegistryTest {
    @Test
    void personMapperIsNotNull() {
        PersonMapper personMapper = (PersonMapper) MapperRegistry.getInstance().getMapper(PersonMapper.class);
        assertNotNull(personMapper);
    }

    @Test
    void domainObjectMapperIsNotNull() {
        DomainObject p = new Person();
        AbstractMapper abstractMapper = MapperRegistry.getInstance().getMapper(p.getClass());
        assertNotNull(abstractMapper);
    }

    @Test
    void domainObjectClassIsEqualsToPersonClass() {
        DomainObject p = new Person();
        Person p1 = new Person();
        assertEquals(p.getClass(), p1.getClass());
    }
}