package com.dm.asde.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yarielinfante on 3/26/17.
 */
@Data
@Entity
public class CaseAttachment  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "INT(1) NOT NULL COMMENT '0-imagen  1-video  2- audio  3-otros'")
    private int type;
    private String url;
    @JsonIgnore
    @JoinColumn(name = "case_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DmCase dmCase;
}
