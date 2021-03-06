package org.com.fernando.compare.processor;

import org.com.fernando.compare.factory.SpecificComparatorFactory;
import org.com.fernando.core.service.DataObjectService;
import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IDataObjectFindableContract;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.util.MessagesWrapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.com.fernando.compare.common.ComparingMsgConstants.DIFF_EQUALS;
import static org.com.fernando.util.encoding.BytesToStringUtil.bytesToString;

@Service
public class ComparatorService {

    /*
     * Here i'm using some core service, but someday i can simple replace the impl with some rest service
     */
    private final IDataObjectFindableContract objectFindableContract;
    private final DataComparableObjectValidator dataComparableObjectValidator;
    private final MessagesWrapper messagesWrapper;
    private final SpecificComparatorFactory specificComparatorFactory;

    public ComparatorService(DataObjectService objectFindableContract,
                             DataComparableObjectValidator dataComparableObjectValidator,
                             MessagesWrapper messagesWrapper,
                             SpecificComparatorFactory specificComparatorFactory) {
        this.objectFindableContract = objectFindableContract;
        this.dataComparableObjectValidator = dataComparableObjectValidator;
        this.messagesWrapper = messagesWrapper;
        this.specificComparatorFactory = specificComparatorFactory;
    }

    public CompareResultDTO compare(String id) {
        try {
            DataComparableDTO dataComparable = objectFindableContract.findComparableByReference(id);
            dataComparableObjectValidator.validateDataToCompare(dataComparable);
            return compare(dataComparable.getContentLeft(), dataComparable.getContentRight());
        } catch (ComparingException e) {
            return createComparingError(e);
        }
    }

    private CompareResultDTO compare(DataContentDTO contentLeft,
                                     DataContentDTO contentRight) {
        boolean sameContent = Objects.equals(
                bytesToString(contentLeft.getRawContent()),
                bytesToString(contentRight.getRawContent()));

        if (sameContent) {
            String message = messagesWrapper.get(DIFF_EQUALS);
            return new CompareResultDTO(sameContent, 200, DIFF_EQUALS, message);
        }

        return findDiffInsights(contentLeft, contentRight);
    }

    private CompareResultDTO findDiffInsights(DataContentDTO contentLeft, DataContentDTO contentRight) {
        IFileSpecificComparator comparator = specificComparatorFactory.getComparatorFor(FileType.JSON);
        return comparator.findDiff(contentLeft, contentRight);
    }

    private CompareResultDTO createComparingError(ComparingException e) {
        return new CompareResultDTO(false, e.getHttpStatus(), e.getCode(), messagesWrapper.get(e.getCode()));
    }
}
