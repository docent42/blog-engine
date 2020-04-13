package com.skillbox.blog.exception;

import java.util.Map;

public class InvalidAttributeException extends RuntimeException {

  private static final String INVALID_ATTR = "Invalid attribute";
  private Map<String, String> errors;

  public InvalidAttributeException(Map<String, String> errors) {
    super(INVALID_ATTR);
    this.errors = errors;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
