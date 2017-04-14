package com.dm.asde.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by yarielinfante on 10/27/16.
 */
@Data
public class SignUpDTO {

    private String name;
    @JsonProperty("last-name")
    private String lastName;
    private String email;
    private String password;
    @JsonProperty("user-type")
    private int userType;


}
