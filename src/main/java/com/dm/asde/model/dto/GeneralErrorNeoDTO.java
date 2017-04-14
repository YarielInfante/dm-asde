package com.dm.asde.model.dto;

import lombok.Data;

/**
 * Created by yarielinfante on 1/17/17.
 */
@Data
public class GeneralErrorNeoDTO {
    private SysError error;

    public GeneralErrorNeoDTO(SysError error){
        this.error = error;
    }

    @Override
    public String toString() {
        return "{" +
                "\"error\":" + error +
                '}';
    }
}
