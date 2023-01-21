package com.exlibris.exbliris.models;

import com.exlibris.exbliris.models.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = User.class)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    private String token;
    private Date date;

    public Library() {super();}
    public Library(Long id, String name, User userId, String token, Date date) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.token = token;
        this.date = date;
    }
}
