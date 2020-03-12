package org.com.fernando.share.contracts;

public interface IDataObjectContract extends IDataObjectFindableContract {
    String saveDataLeft(String refId, String rawContent);
    String saveDataRight(String refId, String rawContent);
}
