package com.man.vin.truck.batch.file.repository;

import com.man.vin.truck.batch.file.document.VinDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VinCodeRepository extends MongoRepository<VinDocument, String> {
}
