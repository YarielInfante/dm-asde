package com.dm.asde.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yarielinfante on 3/26/17.
 */
@Data
@Entity
public class Status implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String name;
    private String description;

}
