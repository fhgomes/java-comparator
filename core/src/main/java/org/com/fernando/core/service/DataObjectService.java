package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.data.DataComparableDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataObjectService {

    private final DataObjectRepository usuarioRepository;
    private final DataObjectValidator dataObjectValidator;
    private final DataObjectFactory dataObjectFactory;

    public DataObjectService(DataObjectRepository usuarioRepository,
                             DataObjectValidator dataObjectValidator,
                             DataObjectFactory dataObjectFactory) {
        this.usuarioRepository = usuarioRepository;
        this.dataObjectValidator = dataObjectValidator;
        this.dataObjectFactory = dataObjectFactory;
    }

    public String saveDataLeft(String refId, String rawContent) {
        return doSaveData(refId, rawContent, ObjectDirection.LEFT);
    }

    public String saveDataRight(String refId, String rawContent) {
        return doSaveData(refId, rawContent, ObjectDirection.RIGHT);
    }

    public DataComparableDTO findDataByReference(String refId) {
        List<DataObject> byReferenceId = usuarioRepository.findByReferenceId(refId);
        return dataObjectFactory.transformDataToDTO(refId, byReferenceId);
    }

    private String doSaveData(String refId, String rawContent, ObjectDirection direction) {
        dataObjectValidator.validateContent(rawContent);
        DataObject saved = usuarioRepository.save(dataObjectFactory.createNewObject(refId, direction, rawContent));
        return saved.getId();
    }

}