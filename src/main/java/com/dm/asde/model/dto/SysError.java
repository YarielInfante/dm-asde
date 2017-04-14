package com.dm.asde.model.dto;

import lombok.Data;

/**
 * Created by yarielinfante on 1/17/17.
 */
@Data
public class SysError {

    private String code;
    private String description;

    public SysError() {

    }

    public SysError(String code, String description) {
        this.code = code;
        this.description = description;
    }


    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + "\"" +
                ", \"description\":\"" + description + "\"" +
                '}';
    }
}
