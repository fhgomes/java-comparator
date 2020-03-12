package org.com.fernando.compare.factory;

import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.exception.ComparingException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.com.fernando.compare.common.ComparingMsgConstants.DIFF_ERROR_NOT_IMPLEMENTED;

@Component
public class SpecificComparatorFactory {
    private final List<IFileSpecificComparator> comparators;

    public SpecificComparatorFactory(List<IFileSpecificComparator> comparators) {
        this.comparators = comparators;
    }

    public IFileSpecificComparator getComparatorFor(FileType fileType) {
        if (CollectionUtils.isEmpty(comparators)) {
            throw new ComparingException(DIFF_ERROR_NOT_IMPLEMENTED);
        }
        return comparators.stream()
                .filter(comparator -> comparator.getFileType().equals(fileType))
                .findFirst().orElseThrow(()->new ComparingException(DIFF_ERROR_NOT_IMPLEMENTED));
    }
}
