package com.mycompany.dao;

import com.mycompany.entity.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that extends a {@link AbstractjdbcDao} and implements the interface {@link RoleDao}.
 * @author Julia Tsukanova
 * @version 1.0
 */
public class JdbcRoleDao extends AbstractjdbcDao implements RoleDao {
    private static final String ROLE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS roles_tb (id IDENTITY NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(250));";
    private static final String ROLE_CREATE = "INSERT INTO roles_tb(name) VALUES( ? );";
    private static final String ROLE_UPDATE = "UPDATE roles_tb SET name = ? WHERE id = ? ;";
    private static final String ROLE_FIND_BY_NAME = "SELECT * FROM roles_tb WHERE name = ? ;";
    private static final String ROLE_FIND_BY_ID = "SELECT * FROM roles_tb WHERE id = ?;";
    private static final String ROLE_DELETE = "DELETE FROM roles_tb WHERE id = ? ;";
    private static final String ROLE_TRUNCATE = "SET FOREIGN_KEY_CHECKS = 0; TRUNCATE TABLE roles_tb; SET FOREIGN_KEY_CHECKS = 1;";
    public static final String ROLE_FIND_ALL = "SELECT * FROM roles_tb ;";

    public void createTable() throws SQLException, IOException {
        setPreparedStatement(getPreparedStatement(ROLE_CREATE_TABLE));
        getPreparedStatement().execute();

        closeResources();
    }

    @Override
    public void create(Role role) throws SQLException, IOException {
        setPreparedStatement(getPreparedStatement(ROLE_CREATE));
        getPreparedStatement().setString(1, role.getName());
        getPreparedStatement().execute();

        getPreparedStatement().getConnection().commit();

        closeResources();
    }

    @Override
    public void update(Role role) throws SQLException, IOException {
        setPreparedStatement(getPreparedStatement(ROLE_UPDATE));
        getPreparedStatement().setString(1, role.getName());
        getPreparedStatement().setLong(2, role.getId());
        getPreparedStatement().executeUpdate();

        getPreparedStatement().getConnection().commit();

        closeResources();
    }

    @Override
    public void remove(Role role) throws SQLException, IOException {
        setPreparedStatement(getPreparedStatement(ROLE_DELETE));
        getPreparedStatement().setLong(1, role.getId());
        getPreparedStatement().executeUpdate();

        getPreparedStatement().getConnection().commit();

    }

    @Override
    public Role findByName(String name) throws IOException, SQLException {
        Role role = new Role();
        setPreparedStatement(getPreparedStatement(ROLE_FIND_BY_NAME));
        getPreparedStatement().setString(1, name);
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            role.setId(getResultSet().getLong("id"));
            role.setName(getResultSet().getString("name"));
        }

        closeResources();

        return role;
    }

    @Override
    public List<Role> getAll() throws IOException, SQLException {
        List<Role> roles = new ArrayList<>();
        setPreparedStatement(getPreparedStatement(ROLE_FIND_ALL));
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            Role role = new Role();
            role.setId(getResultSet().getLong("id"));
            role.setName(getResultSet().getString("name"));
            roles.add(role);
        }

        closeResources();

        return roles;
    }

    public Role findById(long id) throws IOException, SQLException {
        Role role = new Role();
        setPreparedStatement(getPreparedStatement(ROLE_FIND_BY_ID));
        getPreparedStatement().setLong(1, id);
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            role.setId(getResultSet().getLong("id"));
            role.setName(getResultSet().getString("name"));
        }

        closeResources();

        return role;
    }

    public void truncate() throws IOException, SQLException {
        setPreparedStatement(getPreparedStatement(ROLE_TRUNCATE));

        getPreparedStatement().execute();

        closeResources();
    }

}
