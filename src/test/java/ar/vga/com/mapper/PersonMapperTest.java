package ar.vga.com.mapper;

import ar.vga.com.domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    @Test
    void insert() {
        Person p = new Person();
        p.setId(1L);
        p.setFirstName("p1");
        p.setLastName("p2");
        p.setNumberOfDependants(3);
        PersonMapper mapper = new PersonMapper();
        mapper.insert(p);
        assertNotNull(mapper.findByLastName("p2"));
    }
}