package org.com.fernando.share.exception;

public class InvalidDataException extends RuntimeException {

  private final String code;
  private final Object[] msgArgs;
  private final int httpStatus;

  public InvalidDataException(String code) {
    this(code, new Object[]{});
  }

  public InvalidDataException(String code, Object[] msgArgs) {
    super(code);
    this.code = code;
    this.msgArgs = msgArgs;
    this.httpStatus = 412;
  }

  public String getCode() {
    return code;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public Object[] getMsgArgs() {
    return msgArgs;
  }
}