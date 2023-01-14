package ar.vga.com.mapper;

import ar.vga.com.domain.Person;
import ar.vga.com.domain.PersonFinder;
import ar.vga.com.domain.UnitOfWork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    @Test
    void insertWorks() {
        UnitOfWork.newCurrent();
        Person p = Person.create("lstName","frstName", 3);
        UnitOfWork.getCurrent().commit();
        PersonFinder personFinder = (PersonFinder) MapperRegistry.getInstance().getMapper(Person.class);
        assertNotNull(personFinder);
        assertNotNull(personFinder.findById(p.getId()));
        UnitOfWork.clean();
    }

    @Test
    void updateWorks() {
        UnitOfWork.newCurrent();
        Person p = Person.create("lstName","frstName", 3);
        UnitOfWork.getCurrent().commit();
        UnitOfWork.clean();

        UnitOfWork.newCurrent();
        Person p2 = ((PersonFinder) MapperRegistry.getInstance().getMapper(Person.class)).findById(p.getId());
        p2.setLastName("pepito");
        UnitOfWork.getCurrent().commit();
        UnitOfWork.clean();
    }
}