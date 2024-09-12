package com.test.qikserve.qikserve.infra.exception;

public class IntegrationException extends RuntimeException {

    public IntegrationException(Exception e) {
        super(e);
    }

    public IntegrationException(String message, Object... args) {
        super(String.format(message, args));
    }
}
