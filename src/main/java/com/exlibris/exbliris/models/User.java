package com.exlibris.exbliris.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;

    public User() {super();}
    public User(Long id, String username, String password, String email, String name, String surname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
}
