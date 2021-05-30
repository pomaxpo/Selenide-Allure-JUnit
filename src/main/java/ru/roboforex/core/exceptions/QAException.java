package ru.roboforex.core.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QAException extends RuntimeException {

    public QAException(String message) {
        super(message);
        log.error(message);
    }

    public QAException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }
}
