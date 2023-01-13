package ar.vga.com.domain;

public class Person extends DomainObject {
    private String lastName;
    private String firstName;
    private Integer numberOfDependants;

    public Person() {
        super(null);
    }

    public Person(Long id) {
        super(id);
        markNew();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        markDirty();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        markDirty();
    }

    public Integer getNumberOfDependants() {
        return numberOfDependants;
    }

    public void setNumberOfDependants(Integer numberOfDependants) {
        this.numberOfDependants = numberOfDependants;
        markDirty();
    }
}