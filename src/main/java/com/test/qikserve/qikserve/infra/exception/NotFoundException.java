package com.test.qikserve.qikserve.infra.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(Exception e) {
        super(e);
    }

    public NotFoundException(String cause, Object... args) {
        super(String.format(cause, args));
    }
}
