package ar.vga.com.domain;

import java.util.List;

public interface PersonFinder {
    Person findById(Long id);

    List<Person> findByLastName(final String lastName);
}
