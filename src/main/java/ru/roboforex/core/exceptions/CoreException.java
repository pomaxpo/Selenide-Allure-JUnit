package ru.roboforex.core.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoreException extends RuntimeException {

    public CoreException(String message) {
        super(message);
        log.error(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }
}
