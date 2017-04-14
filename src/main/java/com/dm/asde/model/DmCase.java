package com.dm.asde.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * Created by yarielinfante on 3/26/17.
 */
@Data
@Entity
public class DmCase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String description;
    private Float lat;
    private Float lng;
    @JoinColumn(name = "case_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonProperty("case-type")
    private CaseType caseType;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonProperty("user-id")
    private User user;
    private Date date;
    @JsonProperty("status-id")
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dmCase")
    private Set<CaseAttachment> caseAttachments;

    public Long getDate() {
        return date.getTime();
    }

    public void setDate(Long date) {
        this.date = new Date(date);
    }

    public String toString() {
        return "Case [" + id +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DmCase dmCase = (DmCase) o;

        if (!id.equals(dmCase.id)) return false;
        if (!address.equals(dmCase.address)) return false;
        if (!description.equals(dmCase.description)) return false;
        if (!lat.equals(dmCase.lat)) return false;
        if (!lng.equals(dmCase.lng)) return false;
        return date.equals(dmCase.date);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + lat.hashCode();
        result = 31 * result + lng.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }


}
