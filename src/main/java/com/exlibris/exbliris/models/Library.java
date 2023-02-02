package com.exlibris.exbliris.models;

import com.exlibris.exbliris.models.user.Users;
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
        property = "id", scope = Library.class)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users usersId;
    private String token;
    private Date created;

    public Library() {super();}
    public Library(Long id, String name, Users usersId, String token, Date created) {
        this.id = id;
        this.name = name;
        this.usersId = usersId;
        this.token = token;
        this.created = created;
    }
}
