package com.example.musicinfotracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user_entity")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Username can't be blank")
    @Size(min = 3, max = 30, message = "Username's length must be between 3 and 30 symbols")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must contains at least 8 symbols")
    @Column(name = "password")
    private String password;


    public UserEntity() {
    }

    public UserEntity(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
