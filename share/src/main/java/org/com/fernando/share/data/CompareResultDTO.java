package org.com.fernando.share.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompareResultDTO {

  @JsonProperty("validEquals")
  private final boolean validEquals;

  @JsonProperty("status")
  private final int status;

  @JsonProperty("code")
  private final String code;

  @JsonProperty("message")
  private final String message;

  public CompareResultDTO(boolean validEquals, int status, String code, String message) {
    this.validEquals = validEquals;
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public static CompareResultDTO withEquals(String message) {
    return new CompareResultDTO(true, 200, "diff.OK", message);
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public boolean isValidEquals() {
    return validEquals;
  }

  public int getStatus() {
    return status;
  }
}