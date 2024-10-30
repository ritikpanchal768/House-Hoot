package com.example.House_Hoot.Common.ExceptionHandling;

import org.springframework.dao.DataAccessException;

public class DatabaseAccessException extends DataAccessException {
    private final int errorCode;

    public DatabaseAccessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
