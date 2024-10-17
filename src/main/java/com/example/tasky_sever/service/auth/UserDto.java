package com.example.tasky_sever.service.auth;

import com.example.tasky_sever.model.auth.Role;
import com.example.tasky_sever.model.tasks.Task;

import java.util.List;

public class UserDto {
    private Integer Id;
    private String username;
    private String firstName;
    private String lastName;
    private Role role;

    public UserDto(Integer Id, String username, String firstName, String lastName, Role role) {
        this.Id = Id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
