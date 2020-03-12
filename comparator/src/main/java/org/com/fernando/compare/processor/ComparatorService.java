package org.com.fernando.compare.processor;

import org.com.fernando.compare.factory.SpecificComparatorFactory;
import org.com.fernando.core.service.DataObjectService;
import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IDataObjectContract;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.util.MessagesWrapper;
import org.springframework.stereotype.Service;

import static org.com.fernando.compare.common.ComparingMsgConstants.DIFF_EQUALS;

@Service
public class ComparatorService {

    /*
     * Here i'm using some core service, but someday i can simple replace the impl with some rest service
     */
    private final IDataObjectContract dataObjectService;
    private final DataComparableObjectValidator dataComparableObjectValidator;
    private final MessagesWrapper messagesWrapper;
    private final SpecificComparatorFactory specificComparatorFactory;

    public ComparatorService(DataObjectService dataObjectService,
                             DataComparableObjectValidator dataComparableObjectValidator,
                             MessagesWrapper messagesWrapper,
                             SpecificComparatorFactory specificComparatorFactory) {
        this.dataObjectService = dataObjectService;
        this.dataComparableObjectValidator = dataComparableObjectValidator;
        this.messagesWrapper = messagesWrapper;
        this.specificComparatorFactory = specificComparatorFactory;
    }

    public CompareResultDTO compare(String id) {
        try {
            DataComparableDTO dataComparable = dataObjectService.findComparableByReference(id);
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
        boolean sameContent = contentLeft.getRawContent().equals(contentRight.getRawContent());

        if (sameContent) {
            String message = messagesWrapper.get(DIFF_EQUALS);
            return new CompareResultDTO(sameContent, 200, DIFF_EQUALS, message);
        }

        return findDiffInsights(contentLeft, contentRight);
    }

    private CompareResultDTO findDiffInsights(DataContentDTO contentLeft, DataContentDTO contentRight) {
        IFileSpecificComparator comparator = specificComparatorFactory.getComparatorFor(FileType.JSON);
        return comparator.findDiffInsights(contentLeft, contentRight);
    }
}
