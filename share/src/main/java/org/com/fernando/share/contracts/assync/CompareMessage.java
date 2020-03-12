package org.com.fernando.share.contracts.assync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompareMessage {

    @JsonProperty("refId")
    private String refId;

    public String getRefId() {
        return refId;
    }

    public CompareMessage() {
        //serialization
    }

    public CompareMessage(String refId) {
        this.refId = refId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompareMessage that = (CompareMessage) o;
        return refId.equals(that.refId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refId);
    }
}