package ar.vga.com.mapper;

import ar.vga.com.domain.DomainObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapper {
    protected Connection connection;
    protected Map<Long, DomainObject> loadedMap = new HashMap<Long, DomainObject>();

    public AbstractMapper(){
        this.connection = H2.makeConnection();
    }

    protected DomainObject abstractFind(Long id) {
        DomainObject domainObject = loadedMap.get(id);
        if(domainObject != null) {
            return domainObject;
        }
        try {
            final String SQL_FIND = getFindStatement();
            PreparedStatement findStatement = connection.prepareStatement(SQL_FIND);
            findStatement.setLong(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            resultSet.next();
            domainObject = load(resultSet);
        } catch (SQLException sqlException) {
            throw new ApplicationException(sqlException.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return domainObject;
    }

    public List<DomainObject> findMany(StatementSource statementSource) {
        try {
            final String SQL = statementSource.getSql();
            PreparedStatement findStatement = connection.prepareStatement(SQL);
            for(int PARAM_INDEX = 0; PARAM_INDEX < statementSource.getParameters().length; PARAM_INDEX++) {
                Object parameter = statementSource.getParameters()[PARAM_INDEX];
                findStatement.setObject(PARAM_INDEX + 1, parameter);
            }
            ResultSet resultSet = findStatement.executeQuery();
            List<DomainObject> domainObjects = new ArrayList<DomainObject>();
            while (resultSet.next()) {
                DomainObject domainObject = load(resultSet);
                domainObjects.add(domainObject);
            }
            return domainObjects;
        } catch (SQLException sqlException) {
            throw new ApplicationException(sqlException.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    protected abstract String getFindStatement();

    protected DomainObject load(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong(1);
            DomainObject domainObject = loadedMap.get(id);
            if(domainObject != null) {
                return domainObject;
            }
            domainObject = doLoad(resultSet);
            loadedMap.put(id, domainObject);
            //TODO markClean() instead!!
            return domainObject;
        } catch (SQLException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    protected abstract DomainObject doLoad(ResultSet resultSet) throws SQLException;

    public abstract void insert(DomainObject domainObject);

    public abstract void update(DomainObject domainObject);

    public abstract void delete(Long id);
}
