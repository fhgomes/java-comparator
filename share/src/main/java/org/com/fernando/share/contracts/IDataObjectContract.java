package org.com.fernando.share.contracts;

public interface IDataObjectContract extends IDataObjectFindableContract {
    String saveDataLeft(String refId, byte[] rawContent);
    String saveDataRight(String refId, byte[] rawContent);
}
