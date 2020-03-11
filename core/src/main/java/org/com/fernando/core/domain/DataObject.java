package org.com.fernando.core.domain;

import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.ProcessStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Calendar;

@Document(value = "DATA_OBJECT")
public class DataObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    private String referenceId;

    @Indexed
    private ProcessStatus processStatus;

    @Indexed
    private ObjectDirection direction;

    @Indexed
    private Calendar createdDate;

    private String rawContent;
    private String decodedContent;

    protected DataObject() {
        //serialization
    }

    public DataObject(String referenceId, ObjectDirection direction, String rawContent) {
        this.referenceId = referenceId;
        this.direction = direction;
        this.rawContent = rawContent;
        this.createdDate = Calendar.getInstance();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getReferenceId() {
        return referenceId;
    }

    public ObjectDirection getDirection() {
        return direction;
    }
    public String getDecodedContent() {
        return decodedContent;
    }

    public void setDecodedContent(String decodedContent) {
        this.decodedContent = decodedContent;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataObject that = (DataObject) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
