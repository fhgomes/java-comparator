package org.com.fernando.share.exception;

public class InvalidDataException extends RuntimeException {

  private final String code;
  private final int httpStatus;

  public InvalidDataException(String code) {
    this.code = code;
    this.httpStatus = 412;
  }

  public String getCode() {
    return code;
  }

  public int getHttpStatus() {
    return httpStatus;
  }
}