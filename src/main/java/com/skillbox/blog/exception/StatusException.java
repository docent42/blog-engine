package com.skillbox.blog.exception;

import javax.persistence.PersistenceException;

public class StatusException extends PersistenceException {

    public StatusException() {
    }

    public StatusException(String message) {
        super(message);
    }
}