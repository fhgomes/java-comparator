package org.com.fernando.share.contracts;

import org.com.fernando.share.data.CompareResultDTO;

public interface IDataObjectContract extends IDataObjectFindableContract {
    String saveDataLeft(String refId, String rawContent);
    String saveDataRight(String refId, String rawContent);

    void startProcessing(String refId);
    void stopProcessing(String refId);

    void saveCompareResult(String refId, CompareResultDTO result);
}
