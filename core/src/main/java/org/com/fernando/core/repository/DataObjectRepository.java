package org.com.fernando.core.repository;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.share.ObjectDirection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DataObjectRepository extends MongoRepository<DataObject, String> {

  List<DataObject> findByReferenceId(String referenceId);

    boolean existsByReferenceIdAndDirection(String id, ObjectDirection objectDirection);
}