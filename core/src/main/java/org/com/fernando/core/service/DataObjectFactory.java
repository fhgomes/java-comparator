package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.ProcessStatus;
import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class DataObjectFactory {

    public DataObject createNewObject(String refId, ObjectDirection direction, byte[] rawContent) {
        return new DataObject(refId, direction, rawContent);
    }

    public DataComparableDTO transformDataToDTO(String refId, List<DataObject> byReferenceId) {
        DataComparableDTO comparable = new DataComparableDTO();
        comparable.setReferenceId(refId);
        comparable.setProcessStatus(ProcessStatus.WAITING);
        fillBothDirections(byReferenceId, comparable);
        return comparable;
    }

    private void fillBothDirections(List<DataObject> byReferenceId, DataComparableDTO comparable) {
        if (!CollectionUtils.isEmpty(byReferenceId)) {
            byReferenceId.stream()
                    .filter(data -> ObjectDirection.LEFT.equals(data.getDirection()))
                    .findFirst()
                    .ifPresent(data -> comparable.setContentLeft(transformContentDTO(data)));

            byReferenceId.stream()
                    .filter(data -> ObjectDirection.RIGHT.equals(data.getDirection()))
                    .findFirst()
                    .ifPresent(data -> comparable.setContentRight(transformContentDTO(data)));

            //here is something that i'm assuming that we can trust in the information of the saved data
            comparable.setProcessStatus(byReferenceId.get(0).getProcessStatus());
        }
    }

    private DataContentDTO transformContentDTO(DataObject dataObject) {
        DataContentDTO content = new DataContentDTO();
        content.setId(dataObject.getId());
        content.setCreatedDate(dataObject.getCreatedDate());
        content.setDecodedContent(dataObject.getDecodedContent());
        content.setRawContent(dataObject.getRawContent());
        content.setDirection(dataObject.getDirection());
        return content;
    }
}