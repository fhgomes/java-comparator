package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.domain.DataObjectResult;
import org.com.fernando.core.producer.CompareProducer;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.core.repository.DataObjectResultRepository;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.ProcessStatus;
import org.com.fernando.share.contracts.IDataObjectContract;
import org.com.fernando.share.contracts.assync.CompareMessage;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class DataObjectService extends DataObjectFindService implements IDataObjectContract {

    private final DataObjectValidator dataObjectValidator;
    private final CompareProducer compareProducer;

    public DataObjectService(DataObjectRepository dataObjectRepository,
                             DataObjectResultRepository dataObjectResultRepository,
                             DataObjectValidator dataObjectValidator,
                             DataObjectFactory dataObjectFactory,
                             CompareProducer compareProducer) {
        super(dataObjectRepository, dataObjectResultRepository, dataObjectFactory);
        this.dataObjectValidator = dataObjectValidator;
        this.compareProducer = compareProducer;
    }

    @Override
    @Transactional
    public String saveDataLeft(String refId, byte[] rawContent) {
        return doSaveData(refId, rawContent, ObjectDirection.LEFT);
    }

    @Override
    @Transactional
    public String saveDataRight(String refId, byte[] rawContent) {
        return doSaveData(refId, rawContent, ObjectDirection.RIGHT);
    }

    @Override
    @Transactional
    public void startProcessing(String refId) {
        DataObjectResult entity = getResultEntity(refId);

        entity.setReferenceId(refId);
        entity.setProcessStatus(ProcessStatus.PROCESSING);
        entity.setFinishedDate(Calendar.getInstance());
        dataObjectResultRepository.save(entity);
    }

    @Override
    public void stopProcessing(String refId) {
        DataObjectResult entity = getResultEntity(refId);

        entity.setReferenceId(refId);
        entity.setProcessStatus(ProcessStatus.WAITING);
        entity.setFinishedDate(Calendar.getInstance());
        dataObjectResultRepository.save(entity);
    }

    @Override
    @Transactional
    public void saveCompareResult(String refId, CompareResultDTO result) {
        DataObjectResult entity = getResultEntity(refId);
        entity.setReferenceId(refId);
        entity.setCode(result.getCode());
        entity.setStatus(result.getStatus());
        entity.setMessage(result.getMessage());
        entity.setDifferences(result.getDifferences());
        entity.setValidEquals(result.isValidEquals());
        entity.setProcessStatus(ProcessStatus.PROCESSED);
        entity.setFinishedDate(Calendar.getInstance());
        dataObjectResultRepository.save(entity);
    }

    public DataComparableDTO findComparableByReference(String refId) {
        return super.findComparableByReference(refId);
    }

    private String doSaveData(String refId, byte[] rawContent, ObjectDirection direction) {
        dataObjectValidator.validateContent(rawContent);
        dataObjectValidator.validateAlreadyExist(refId, direction);
        DataObject saved = dataObjectRepository.save(dataObjectFactory.createNewObject(refId, direction, rawContent));
        compareProducer.produce(new CompareMessage(refId));
        return saved.getId();
    }

    private DataObjectResult getResultEntity(String refId) {
        return dataObjectResultRepository.findByReferenceId(refId).orElse(new DataObjectResult());
    }

}