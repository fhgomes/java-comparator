package org.com.fernando.share.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.com.fernando.share.ProcessStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataComparableDTO {

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("processStatus")
    private ProcessStatus processStatus;

    @JsonProperty("contentLeft")
    private DataContentDTO contentLeft;

    @JsonProperty("contentRight")
    private DataContentDTO contentRight;

    public String getReferenceId() {
        return referenceId;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public DataContentDTO getContentLeft() {
        return contentLeft;
    }

    public void setContentLeft(DataContentDTO contentLeft) {
        this.contentLeft = contentLeft;
    }

    public DataContentDTO getContentRight() {
        return contentRight;
    }

    public void setContentRight(DataContentDTO contentRight) {
        this.contentRight = contentRight;
    }
}
