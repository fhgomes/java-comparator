package org.com.fernando.share.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvalidResultDTO {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  public InvalidResultDTO() {
    //to serialization
  }

  public InvalidResultDTO(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}