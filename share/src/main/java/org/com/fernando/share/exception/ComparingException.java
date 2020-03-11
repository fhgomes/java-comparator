package org.com.fernando.share.exception;

public class ComparingException extends RuntimeException {

  private final String code;
  private final int httpStatus;

  public ComparingException(String code) {
    super(code);
    this.code = code;
    this.httpStatus = 409;
  }

  public String getCode() {
    return code;
  }

  public int getHttpStatus() {
    return httpStatus;
  }
}