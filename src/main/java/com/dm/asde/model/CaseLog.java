package com.dm.asde.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by yarielinfante on 3/26/17.
 */
@Data
@Entity
public class CaseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DmCase dmCase;
    @Column(length = 50)
    private String status;
    private Date date;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    private String comment;
}
