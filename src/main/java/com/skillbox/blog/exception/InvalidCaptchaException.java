/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2020 VTB Group. All rights reserved.
 */

package com.skillbox.blog.exception;

public class InvalidCaptchaException extends RuntimeException {

  public InvalidCaptchaException() {
  }

  public InvalidCaptchaException(String message) {
    super(message);
  }
}