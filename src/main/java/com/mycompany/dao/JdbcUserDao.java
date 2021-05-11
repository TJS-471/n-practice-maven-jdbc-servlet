package com.mycompany.dao;

import com.mycompany.entity.Role;
import com.mycompany.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that extends a {@link AbstractjdbcDao} and implements the interface {@link UserDao}.
 * @author Julia Tsukanova
 * @version 1.0
 */
public class JdbcUserDao extends AbstractjdbcDao implements UserDao {
    private static final String USER_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users_tb (id IDENTITY NOT NULL AUTO_INCREMENT PRIMARY KEY, login VARCHAR(250),"
            + "email VARCHAR(255), password VARCHAR(255), first_name VARCHAR(255), last_name VARCHAR(255), birth_date DATE, role_id BIGINT DEFAULT NULL, FOREIGN KEY(role_id) REFERENCES roles_tb(id)  ON DELETE SET NULL\n" +
            "    ON UPDATE CASCADE);";
    private static final String USER_CREATE = "INSERT INTO users_tb(LOGIN, EMAIL, password, first_name, last_name, birth_date, role_id) VALUES( ?, ?, ?, ?, ?, ?, ?);";
    private static final String USER_UPDATE = "UPDATE users_tb SET login = ?, email = ?, password = ?, first_name = ?, last_name = ?, birth_date = ? , role_id = ? WHERE id = ? ;";
    private static final String USER_FIND_BY_LOGIN = "SELECT * FROM users_tb WHERE login = ? LIMIT 1;";
    private static final String USER_FIND_BY_EMAIL = "SELECT * FROM users_tb WHERE email = ? LIMIT 1;";
    private static final String USER_FIND_ALL = "SELECT * FROM users_tb ;";
    private static final String USER_DELETE = "DELETE FROM users_tb WHERE ID = ? ;";
    private static final String USER_TRUNCATE = "SET FOREIGN_KEY_CHECKS = 0; TRUNCATE TABLE users_tb ; SET FOREIGN_KEY_CHECKS = 1;";
    public static final String USER_FIND_BY_ID = "SELECT * FROM users_tb WHERE ID = ? ;";
    private JdbcRoleDao dao = new JdbcRoleDao();

    public void createTable() throws IOException, SQLException {
        setPreparedStatement(getPreparedStatement(USER_CREATE_TABLE));
        getPreparedStatement().execute();

        closeResources();
    }

    @Override
    public void create(User user) throws IOException, SQLException {
        setPreparedStatement(getPreparedStatement(USER_CREATE));
        getPreparedStatement().setString(1, user.getLogin());
        getPreparedStatement().setString(2, user.getEmail());
        getPreparedStatement().setString(3, user.getPassword());
        getPreparedStatement().setString(4, user.getFirstName());
        getPreparedStatement().setString(5, user.getLastName());
        getPreparedStatement().setDate(6, new java.sql.Date(user.getBirthDate().getTime()));
        List<Role> roles = user.getRoles();
        for(Role role : roles){
            Role byName = dao.findByName(role.getName());
            if(byName != null){
                getPreparedStatement().setLong(7, byName.getId());
            }
        }
        getPreparedStatement().execute();

        getPreparedStatement().getConnection().commit();

        closeResources();
    }

    @Override
    public void update(User user) throws IOException, SQLException {
        setPreparedStatement(getPreparedStatement(USER_UPDATE));
        getPreparedStatement().setString(1, user.getLogin());
        getPreparedStatement().setString(2, user.getEmail());
        getPreparedStatement().setString(3, user.getPassword());
        getPreparedStatement().setString(4, user.getFirstName());
        getPreparedStatement().setString(5, user.getLastName());
        getPreparedStatement().setDate(6, new java.sql.Date(user.getBirthDate().getTime()));
        List<Role> roles = user.getRoles();
        for(Role role : roles){
            Role byName = dao.findByName(role.getName());
            if(byName != null){
                getPreparedStatement().setLong(7, byName.getId());
            }
        }
        getPreparedStatement().setLong(8, user.getId());
        getPreparedStatement().executeUpdate();

        getPreparedStatement().getConnection().commit();

        closeResources();
    }

    @Override
    public void remove(long id) throws IOException, SQLException {
        setPreparedStatement(getPreparedStatement(USER_DELETE));
        getPreparedStatement().setLong(1, id);
        getPreparedStatement().executeUpdate();

        getPreparedStatement().getConnection().commit();

        closeResources();
    }

    @Override
    public List<User> findAll() throws SQLException, IOException {
        List<User> users = new ArrayList<>();
        setPreparedStatement(getPreparedStatement(USER_FIND_ALL));
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            User user = new User();
            user.setId(getResultSet().getLong("id"));
            user.setLogin(getResultSet().getString("login"));
            user.setEmail(getResultSet().getString("email"));
            user.setPassword(getResultSet().getString("password"));
            user.setFirstName(getResultSet().getString("first_name"));
            user.setLastName(getResultSet().getString("last_name"));
            java.sql.Date sqlDate = getResultSet().getDate("birth_date");
            java.util.Date utilBirthDate = new java.util.Date(sqlDate.getTime());
            user.setBirthDate(utilBirthDate);
            Role role = dao.findById(getResultSet().getLong("role_id"));
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            users.add(user);
        }

        closeResources();

        return users;
    }

    @Override
    public User findByLogin(String login) throws IOException, SQLException {
        User user = null;
        setPreparedStatement(getPreparedStatement(USER_FIND_BY_LOGIN));
        getPreparedStatement().setString(1, login);
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            user = new User();
            user.setId(getResultSet().getLong("id"));
            user.setLogin(getResultSet().getString("login"));
            user.setEmail(getResultSet().getString("email"));
            user.setPassword(getResultSet().getString("password"));
            user.setFirstName(getResultSet().getString("first_name"));
            user.setLastName(getResultSet().getString("last_name"));
            java.sql.Date sqlDate = getResultSet().getDate("birth_date");
            java.util.Date utilBirthDate = new java.util.Date(sqlDate.getTime());
            user.setBirthDate(utilBirthDate);
            Role role = dao.findById(getResultSet().getLong("role_id"));
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        }

        closeResources();

        return user;
    }

    @Override
    public User findByEmail(String email) throws IOException, SQLException {
        User user = null;
        setPreparedStatement(getPreparedStatement(USER_FIND_BY_EMAIL));
        getPreparedStatement().setString(1, email);
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            user = new User();
            user.setId(getResultSet().getLong("id"));
            user.setLogin(getResultSet().getString("login"));
            user.setEmail(getResultSet().getString("email"));
            user.setPassword(getResultSet().getString("password"));
            user.setFirstName(getResultSet().getString("first_name"));
            user.setLastName(getResultSet().getString("last_name"));
            java.sql.Date sqlDate = getResultSet().getDate("birth_date");
            java.util.Date utilBirthDate = new java.util.Date(sqlDate.getTime());
            user.setBirthDate(utilBirthDate);
            Role role = dao.findById(getResultSet().getLong("role_id"));
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        }

        closeResources();

        return user;
    }

    @Override
    public User findById(long id) throws SQLException, IOException {
        User user = null;
        setPreparedStatement(getPreparedStatement(USER_FIND_BY_ID));
        getPreparedStatement().setLong(1, id);
        setResultSet(getPreparedStatement().executeQuery());
        while (getResultSet().next()) {
            user = new User();
            user.setId(getResultSet().getLong("id"));
            user.setLogin(getResultSet().getString("login"));
            user.setEmail(getResultSet().getString("email"));
            user.setPassword(getResultSet().getString("password"));
            user.setFirstName(getResultSet().getString("first_name"));
            user.setLastName(getResultSet().getString("last_name"));
            java.sql.Date sqlDate = getResultSet().getDate("birth_date");
            java.util.Date utilBirthDate = new java.util.Date(sqlDate.getTime());
            user.setBirthDate(utilBirthDate);
            Role role = dao.findById(getResultSet().getLong("role_id"));
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        }

        closeResources();

        return user;
    }

    public void truncate() throws IOException, SQLException {
        setPreparedStatement(getPreparedStatement(USER_TRUNCATE));

        getPreparedStatement().execute();

        closeResources();
    }
}
