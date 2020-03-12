package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.domain.DataObjectResult;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.core.repository.DataObjectResultRepository;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.ProcessStatus;
import org.com.fernando.share.contracts.IDataObjectFindableContract;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataObjectFindService implements IDataObjectFindableContract {

    protected final DataObjectRepository dataObjectRepository;
    protected final DataObjectResultRepository dataObjectResultRepository;
    protected final DataObjectFactory dataObjectFactory;

    public DataObjectFindService(DataObjectRepository dataObjectRepository,
                                 DataObjectResultRepository dataObjectResultRepository,
                                 DataObjectFactory dataObjectFactory) {
        this.dataObjectRepository = dataObjectRepository;
        this.dataObjectResultRepository = dataObjectResultRepository;
        this.dataObjectFactory = dataObjectFactory;
    }

    public DataComparableDTO findComparableByReference(String refId) {
        List<DataObject> byReferenceId = dataObjectRepository.findByReferenceId(refId);
        return dataObjectFactory.transformDataToDTO(refId, byReferenceId);
    }

    public boolean existsByReferenceAndDirection(String id, ObjectDirection objectDirection) {
        return dataObjectRepository.existsByReferenceIdAndDirection(id, objectDirection);
    }

    @Override
    public CompareResultDTO findCompareResult(String refId) {
        Optional<DataObjectResult> byReferenceId = dataObjectResultRepository.findByReferenceId(refId);
        if (!byReferenceId.isPresent()) {
            return null;
        }
        DataObjectResult dataObjectResult = byReferenceId.get();
        if (!dataObjectResult.getProcessStatus().equals(ProcessStatus.PROCESSED)) {
            return null;
        }
        return new CompareResultDTO(dataObjectResult.isValidEquals(),
                dataObjectResult.getStatus(),
                dataObjectResult.getCode(),
                dataObjectResult.getMessage()
        );
    }
}