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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CompareResultDTO resultDTO = (CompareResultDTO) o;

    if (status != resultDTO.status) {
      return false;
    }
    return code != null ? code.equals(resultDTO.code) : resultDTO.code == null;
  }

  @Override
  public int hashCode() {
    int result = status;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    return result;
  }
}