package org.com.fernando.share.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompareResultDTO {

  @JsonProperty("validEquals")
  private boolean validEquals;

  @JsonProperty("status")
  private int status;

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("differences")
  private List<String> differences = new ArrayList<>();

  public CompareResultDTO() {
    //to serialization
  }

  public CompareResultDTO(boolean validEquals, int status, String code, String message) {
    this.validEquals = validEquals;
    this.status = status;
    this.code = code;
    this.message = message;
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

  public List<String> getDifferences() {
    return differences;
  }

  public void addDifference(String difference) {
    this.differences.add(difference);
  }
}