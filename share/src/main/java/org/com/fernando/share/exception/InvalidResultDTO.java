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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    InvalidResultDTO that = (InvalidResultDTO) o;

    return code != null ? code.equals(that.code) : that.code == null;
  }

  @Override
  public int hashCode() {
    return code != null ? code.hashCode() : 0;
  }
}