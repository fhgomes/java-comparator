package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.share.exception.InvalidDataException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DataObjectValidator {

    /**
     * Here i'm assuming that the min size of any econded String will be 4.
     * If you encode and empty Json {} with base64 it will be e30=
     */
    private static final int MIN_SIZE_ENCODED = 4;


    public void validateContent(String rawContent) {
        //i'm assuming that we don't allow to save empty or invalid data
        if (StringUtils.isEmpty(rawContent)) {
           throw new InvalidDataException("content.invalid.empty");
        }
        if (rawContent.length() < MIN_SIZE_ENCODED) {
            throw new InvalidDataException("content.invalid.min_size");
        }

        //Here i can improve doing some base64 validation or Json validation, it depends on the requirements
        //We also can validate it after using an asynchronous process and save the entity with invalid state.
        //Maybe include a new field in DataObject -> validationStatus
    }
}