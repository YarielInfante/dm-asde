package com.dm.asde.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yarielinfante on 3/26/17.
 */
@Data
@Entity
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Role role;
}
