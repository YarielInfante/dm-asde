package com.dm.asde.exception;

/**
 * Created by yarielinfante on 1/17/17.
 */
public class InvalidUserException extends DmAPIException {

    {
        setExceptionLevel(ExceptionLevel.WARN);
    }

    public InvalidUserException() {
    }

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(Exception e) {
        super(e);
    }

    public InvalidUserException(Object requestParams) {
        super(requestParams);
    }
}
