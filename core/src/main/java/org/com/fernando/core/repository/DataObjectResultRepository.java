package org.com.fernando.core.repository;

import org.com.fernando.core.domain.DataObjectResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DataObjectResultRepository extends MongoRepository<DataObjectResult, String> {

    Optional<DataObjectResult> findByReferenceId(String referenceId);
}