package org.com.fernando.compare.processor;

import org.com.fernando.core.service.DataObjectService;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.util.MessagesWrapper;
import org.springframework.stereotype.Service;

@Service
public class ComparatorService {

    /**
     * Here i'm using some core service, but someday i can simple replace it by some rest service
     */
    //TODO replace by some interface
    private final DataObjectService dataObjectService;
    private final DataComparableObjectValidator dataComparableObjectValidator;
    private final MessagesWrapper messagesWrapper;

    public ComparatorService(DataObjectService dataObjectService,
                             DataComparableObjectValidator dataComparableObjectValidator,
                             MessagesWrapper messagesWrapper) {
        this.dataObjectService = dataObjectService;
        this.dataComparableObjectValidator = dataComparableObjectValidator;
        this.messagesWrapper = messagesWrapper;
    }

    public CompareResultDTO compare(String id) {
        try {
            DataComparableDTO dataComparable = dataObjectService.findDataByReference(id);
            dataComparableObjectValidator.validateDataToCompare(dataComparable);
            return compare(dataComparable.getContentLeft(), dataComparable.getContentRight());
        } catch (ComparingException e) {
            return createComparingError(e);
        }
    }

    private CompareResultDTO createComparingError(ComparingException e) {
        return new CompareResultDTO(false, e.getHttpStatus(), e.getCode(), "");
    }

    private CompareResultDTO compare(DataContentDTO contentLeft,
                                     DataContentDTO contentRight) {
        CompareResultDTO result = simpleCompareSize(contentLeft, contentRight);

        return result;
    }

    private CompareResultDTO simpleCompareSize(DataContentDTO contentLeft, DataContentDTO contentRight) {
        boolean equalSize = contentLeft.getRawContent().length() == contentRight.getRawContent().length();
        int status = (equalSize) ? 200 : 409;
        String code = (equalSize) ? "diff.size_not_equals" : "diff.size_equals";
        String message = messagesWrapper.get(code);
        return new CompareResultDTO(equalSize, status, code, message);
    }
}
