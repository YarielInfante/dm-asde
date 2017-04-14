package com.dm.asde.exception;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Created by yarielinfante on 2/6/17.
 */
public class ExpiredSessionException extends DmAPIException {

    {
        setExceptionLevel(ExceptionLevel.WARN);
    }

    public ExpiredSessionException(ExpiredJwtException expiredJwtException) {
        super(expiredJwtException);
    }
}
