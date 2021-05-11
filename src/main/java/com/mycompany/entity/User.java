package com.mycompany.entity;

import java.util.Date;
import java.util.List;

/**
 * A simple Java domain class that represents a {@link User}.
 * @author Julia Tsukanova
 * @version 1.0
 */
public class User {
    private long id;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private List<Role> roles;

    public User(long id, String login, String email, String password, String firstName, String lastName, Date birthDate, List<Role> roles) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public User(long id, String email, String password, String firstName, String lastName, Date birthDate, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public User(long id, String login, String email, String password, String firstName, String lastName, Date birthDate) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public User(String login, String email, String password, String firstName, String lastName, Date birthDate, List<Role> roles) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.roles = roles;
    }

    public User(String login, String email, String password, String firstName, String lastName, Date birthDate) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
