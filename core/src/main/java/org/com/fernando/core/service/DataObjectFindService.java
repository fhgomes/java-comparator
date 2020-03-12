package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.contracts.IDataObjectFindableContract;
import org.com.fernando.share.data.DataComparableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataObjectFindService implements IDataObjectFindableContract {

    protected final DataObjectRepository dataObjectRepository;
    protected final DataObjectFactory dataObjectFactory;

    public DataObjectFindService(DataObjectRepository dataObjectRepository,
                                 DataObjectFactory dataObjectFactory) {
        this.dataObjectRepository = dataObjectRepository;
        this.dataObjectFactory = dataObjectFactory;
    }

    public DataComparableDTO findComparableByReference(String refId) {
        List<DataObject> byReferenceId = dataObjectRepository.findByReferenceId(refId);
        return dataObjectFactory.transformDataToDTO(refId, byReferenceId);
    }

    public boolean existsByReferenceAndDirection(String id, ObjectDirection objectDirection) {
        return dataObjectRepository.existsByReferenceIdAndDirection(id, objectDirection);
    }
}