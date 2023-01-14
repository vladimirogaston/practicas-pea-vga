package ar.vga.com.domain;

public class Person extends DomainObject {
    private String lastName;
    private String firstName;
    private Integer numberOfDependants;

    public Person() {
        super(null);
    }

    public Person(Long id,String lastName, String firstName, Integer numberOfDependants) {
        super(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.numberOfDependants = numberOfDependants;
        markNew();
    }

    public static Person create(String lastName, String firstName, Integer numberOfDependants) {
        Long id = IDGenerator.nextID();
        Person person = new Person(id, lastName, firstName, numberOfDependants);
        return person;
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