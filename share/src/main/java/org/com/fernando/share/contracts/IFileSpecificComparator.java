package org.com.fernando.share.contracts;

import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataContentDTO;

public interface IFileSpecificComparator {
    CompareResultDTO findDiffInsights(DataContentDTO contentLeft, DataContentDTO contentRight);
    FileType getFileType();
}
