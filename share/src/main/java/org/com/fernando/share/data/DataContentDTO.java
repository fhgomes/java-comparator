package org.com.fernando.share.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.com.fernando.share.ObjectDirection;

import java.util.Calendar;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataContentDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("direction")
    private ObjectDirection direction;

    @JsonProperty("date")
    private Calendar createdDate;

    @JsonProperty("rawContent")
    private String rawContent;

    @JsonProperty("decodedContent")
    private String decodedContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectDirection getDirection() {
        return direction;
    }

    public void setDirection(ObjectDirection direction) {
        this.direction = direction;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getDecodedContent() {
        return decodedContent;
    }

    public void setDecodedContent(String decodedContent) {
        this.decodedContent = decodedContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataContentDTO that = (DataContentDTO) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
