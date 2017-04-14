package com.dm.asde.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yarielinfante on 10/27/16.
 */
@Data
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String lastName;
    @Column(unique = true, nullable = false, length = 30)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(columnDefinition = " INT(1) COMMENT '0 -Regular and 1- Admin'")
    private byte userType;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private boolean blocked;


    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    @PrePersist
    void preInsert() {
        active = true;
        blocked = false;
    }

}
