package org.com.fernando.compare.processor;

import org.com.fernando.compare.factory.SpecificComparatorFactory;
import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IDataObjectContract;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.util.MessagesWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.com.fernando.compare.common.ComparingMsgConstants.DIFF_EQUALS;

@Service
public class ComparatorService {

    /*
     * Here i'm using some core service, but someday i can simple replace the impl with some rest service
     */
    private final IDataObjectContract dataObjectContract;
    private final DataComparableObjectValidator dataComparableObjectValidator;
    private final MessagesWrapper messagesWrapper;
    private final SpecificComparatorFactory specificComparatorFactory;

    public ComparatorService(IDataObjectContract dataObjectContract,
                             DataComparableObjectValidator dataComparableObjectValidator,
                             MessagesWrapper messagesWrapper,
                             SpecificComparatorFactory specificComparatorFactory) {
        this.dataObjectContract = dataObjectContract;
        this.dataComparableObjectValidator = dataComparableObjectValidator;
        this.messagesWrapper = messagesWrapper;
        this.specificComparatorFactory = specificComparatorFactory;
    }

    @Transactional
    public CompareResultDTO compare(String refId) {
        try {
            CompareResultDTO processedResult = dataObjectContract.findCompareResult(refId);
            if (processedResult != null) {
                return processedResult;
            }

            return doStartCompare(refId);
        } catch (ComparingException e) {
            dataObjectContract.stopProcessing(refId);
            return createComparingError(e);
        }
    }

    private CompareResultDTO doStartCompare(String refId) {
        dataObjectContract.startProcessing(refId);
        DataComparableDTO dataComparable = dataObjectContract.findComparableByReference(refId);
        dataComparableObjectValidator.validateDataToCompare(dataComparable);
        CompareResultDTO result = compare(dataComparable.getContentLeft(), dataComparable.getContentRight());
        saveResult(refId, result);
        return result;
    }

    private void saveResult(String refId, CompareResultDTO result) {
        dataObjectContract.saveCompareResult(refId, result);
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
        return comparator.findDiff(contentLeft, contentRight);
    }

    private CompareResultDTO createComparingError(ComparingException e) {
        return new CompareResultDTO(false, e.getHttpStatus(), e.getCode(), messagesWrapper.get(e.getCode()));
    }
}
