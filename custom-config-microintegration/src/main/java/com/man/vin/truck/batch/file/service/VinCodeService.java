package com.man.vin.truck.batch.file.service;

import com.man.vin.truck.batch.file.document.VinDocument;
import com.man.vin.truck.batch.file.repository.VinCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class VinCodeService {

    @Autowired
    private VinCodeRepository repository;

    public void save(Map<String, VinDocument> documents){
        for (String vin : documents.keySet()) {
            Optional<VinDocument> byId = repository.findById(vin);
            if(byId.isPresent()){
                VinDocument vinDocument = byId.get();
                vinDocument.addHardCode(documents.get(vin).getHardCode());
                vinDocument.addSoftCode(documents.get(vin).getSoftCode());
                repository.save(vinDocument);
            }else {
                repository.insert(documents.get(vin));
            }
        }
    }
}
