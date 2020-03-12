package org.com.fernando.core.service;

import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.contracts.IDataObjectFindableContract;
import org.com.fernando.share.exception.InvalidDataException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.com.fernando.core.common.ErrorMsgConstants.*;

@Service
public class DataObjectValidator {

    private final IDataObjectFindableContract dataObjectFindService;

    /*
     * Here i'm assuming that the min size of any econded String will be 4.
     * If you encode and empty Json {} with base64 it will be e30=
     */
    private static final int MIN_SIZE_ENCODED = 4;

    public DataObjectValidator(DataObjectFindService dataObjectFindService) {
        this.dataObjectFindService = dataObjectFindService;
    }

    public void validateAlreadyExist(String id, ObjectDirection objectDirection) {
        boolean exist = dataObjectFindService.existsByReferenceAndDirection(id, objectDirection);
        if (exist) {
            throw new InvalidDataException(CONTENT_DUPLICATED);
        }
    }

    public void validateContent(String rawContent) {
        //i'm assuming that we don't allow to save empty or invalid data
        if (StringUtils.isEmpty(rawContent)) {
           throw new InvalidDataException(CONTENT_INVALID_EMPTY);
        }
        if (rawContent.length() < MIN_SIZE_ENCODED) {
            throw new InvalidDataException(CONTENT_INVALID_MIN_SIZE);
        }

        //Here i can improve doing some base64 validation or Json validation, it depends on the requirements
        //We also can validate it after using an asynchronous process and save the entity with invalid state.
        //Maybe include a new field in DataObject -> validationStatus
    }
}