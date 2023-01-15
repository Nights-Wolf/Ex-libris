package com.exlibris.exbliris.models.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String surname;

    public UserResponse() {super();}

    public UserResponse(Long id, String username, String email, String name, String surname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
}
