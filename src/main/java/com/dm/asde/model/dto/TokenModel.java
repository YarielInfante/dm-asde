package com.dm.asde.model.dto;

import lombok.Data;

/**
 * Created by yarielinfante on 3/26/17.
 */
@Data
public class TokenModel {

    private Long userId;
    private String email;
    private int userType;

    public TokenModel() {

    }

    public TokenModel(Long userId, String email, int userType) {
        this.userId = userId;
        this.email = email;
        this.userType = userType;
    }
}
