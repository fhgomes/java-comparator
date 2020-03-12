package org.com.fernando.share.contracts;

import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.data.DataComparableDTO;

public interface IDataObjectFindableContract {
    DataComparableDTO findComparableByReference(String refId);
    boolean existsByReferenceAndDirection(String id, ObjectDirection objectDirection);
}
