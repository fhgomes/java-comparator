package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.contracts.IDataObjectContract;
import org.com.fernando.share.data.DataComparableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataObjectService extends DataObjectFindService implements IDataObjectContract {

    private final DataObjectValidator dataObjectValidator;

    public DataObjectService(DataObjectRepository dataObjectRepository,
                             DataObjectValidator dataObjectValidator,
                             DataObjectFactory dataObjectFactory) {
        super(dataObjectRepository, dataObjectFactory);
        this.dataObjectValidator = dataObjectValidator;
    }

    public String saveDataLeft(String refId, byte[] rawContent) {
        return doSaveData(refId, rawContent, ObjectDirection.LEFT);
    }

    public String saveDataRight(String refId, byte[] rawContent) {
        return doSaveData(refId, rawContent, ObjectDirection.RIGHT);
    }

    public DataComparableDTO findComparableByReference(String refId) {
        List<DataObject> byReferenceId = dataObjectRepository.findByReferenceId(refId);
        return dataObjectFactory.transformDataToDTO(refId, byReferenceId);
    }

    private String doSaveData(String refId, byte[] rawContent, ObjectDirection direction) {
        dataObjectValidator.validateContent(rawContent);
        dataObjectValidator.validateAlreadyExist(refId, direction);
        DataObject saved = dataObjectRepository.save(dataObjectFactory.createNewObject(refId, direction, rawContent));
        return saved.getId();
    }

}