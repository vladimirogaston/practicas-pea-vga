package ar.vga.com.mapper;

import ar.vga.com.domain.DomainObject;
import ar.vga.com.domain.Person;
import ar.vga.com.domain.PersonFinder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper extends AbstractMapper implements PersonFinder {
    public static final String COLUMNS = "id, last_name, first_name, number_of_dependants";
    private static final String FIND_BY_ID_STATEMENT = "SELECT " + COLUMNS + " FROM persons P WHERE P.id = ?";
    private static final String INSERT_STATEMENT = "INSERT INTO persons (id, last_name, first_name, number_of_dependants) VALUES (?,?,?,?)";
    private static final String UPDATE_STATEMENT = "UPDATE persons SET last_name = ?, first_name = ?, number_of_dependants = ? WHERE id = ?";
    private static final String DELETE_STATEMENT = "DELETE persons P WHERE P.id = ?";

    public PersonMapper(){
        super();
    }

    protected String getFindStatement() {
        return FIND_BY_ID_STATEMENT;
    }

    @Override
    public Person findById(final Long id) {
        return (Person) abstractFind(id);
    }

    @Override
    public List<Person> findByLastName(final String lastname) {
        StatementSource statementSource = new StatementSource() {
            public String getSql() {
                return "SELECT " + COLUMNS + " FROM persons P WHERE p.last_name = ?";
            }

            public Object[] getParameters() {
                return new Object[]{lastname};
            }
        };
        List<Person> personList = findMany(statementSource).stream()
                .map(domainObject -> (Person)domainObject)
                .collect(Collectors.toList());
        return personList;
    }

    protected DomainObject doLoad(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String lastName = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        Integer numberOfDependants = resultSet.getInt(4);
        Person person = new Person(id, lastName, firstName, numberOfDependants);
        return person;
    }

    @Override
    public void insert(DomainObject domainObject) {
        Person person = (Person) domainObject;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATEMENT);
            preparedStatement.setObject(1, person.getId());
            preparedStatement.setObject(2, person.getLastName());
            preparedStatement.setObject(3, person.getFirstName());
            preparedStatement.setObject(4, person.getNumberOfDependants());
            int res = preparedStatement.executeUpdate();
            System.out.println("----- RESULT OF INSERT ----- " + res);
        } catch (SQLException exception) {
            throw new ApplicationException(exception.getMessage());
        }
    }

    public void update(DomainObject domainObject) {
        try {
            Person person = (Person) domainObject;
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATEMENT);
            preparedStatement.setObject(1, person.getLastName());
            preparedStatement.setObject(2, person.getFirstName());
            preparedStatement.setObject(3, person.getNumberOfDependants());
            preparedStatement.setObject(4, person.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("----- RESULT OF UPDATE ----- " + res);
        } catch (SQLException exception) {
            throw new ApplicationException(exception.getMessage());
        }
    }

    public void delete(final Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STATEMENT);
            preparedStatement.setObject(1, id);
            int res = preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new ApplicationException(exception.getMessage());
        }
    }
}