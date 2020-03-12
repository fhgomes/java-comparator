package org.com.fernando.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.com.fernando.share.ProcessStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Document(value = "DATA_OBJECT")
public class DataObjectResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Indexed
    private String referenceId;

    @Indexed
    private ProcessStatus processStatus;

    @Indexed
    private Calendar finishedDate;

    @Indexed
    private String code;

    private int status;

    private String message;

    @Indexed
    private boolean validEquals;

    private List<String> differences = new ArrayList<>();

    public DataObjectResult() {
        //serialization
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public Calendar getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Calendar finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValidEquals() {
        return validEquals;
    }

    public void setValidEquals(boolean validEquals) {
        this.validEquals = validEquals;
    }

    public List<String> getDifferences() {
        return differences;
    }

    public void setDifferences(List<String> differences) {
        this.differences = differences;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataObjectResult that = (DataObjectResult) o;

        return referenceId.equals(that.referenceId);
    }

    @Override
    public int hashCode() {
        return referenceId.hashCode();
    }
}
