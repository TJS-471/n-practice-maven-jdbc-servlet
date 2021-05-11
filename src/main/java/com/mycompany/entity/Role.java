package com.mycompany.entity;

/**
 * A simple Java class that represents a role of a {@link User}.
 * @author Julia Tsukanova
 * @version 1.0
 */

public class Role {
    private long id;
    private String name;

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
