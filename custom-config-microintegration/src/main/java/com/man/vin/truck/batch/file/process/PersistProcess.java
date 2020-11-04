package com.man.vin.truck.batch.file.process;

import com.man.vin.truck.batch.file.document.VinDocument;
import com.man.vin.truck.batch.file.dataformat.CsvItem;
import com.man.vin.truck.batch.file.repository.VinCodeRepository;
import com.man.vin.truck.batch.file.service.VinCodeService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersistProcess implements Processor {

    @Autowired
    private VinCodeService service;

    @Override
    public void process(Exchange exchange) throws Exception {
        List<CsvItem> bulk= exchange.getIn().getBody(List.class);
        Map<String, VinDocument> documents = new HashMap<>();

        for (CsvItem item: bulk) {
            parseDocumentsByFile(documents, item);
        }
       service.save(documents);
    }

    private void parseDocumentsByFile(Map<String, VinDocument> documents, CsvItem item) {
        if(item.getFileName().contains("hard")){
            persistHardCode(documents, item);
        }else if(item.getFileName().contains("soft")){
            persistSoftCode(documents, item);
        }
    }

    private void persistSoftCode(Map<String, VinDocument> documents, CsvItem item) {
        if(documents.containsKey(item.getVin())){
            documents.get(item.getVin()).addSoftCode(item.getCode());
        }else{
            VinDocument vinDocument = new VinDocument(item.getVin());
            vinDocument.addSoftCode(item.getCode());
            documents.put(item.getVin(), vinDocument);
        }
    }

    private void persistHardCode(Map<String, VinDocument> documents, CsvItem item) {
        if(documents.containsKey(item.getVin())){
            documents.get(item.getVin()).addHardCode(item.getCode());
        }else{
            VinDocument vinDocument = new VinDocument(item.getVin());
            vinDocument.addHardCode(item.getCode());
            documents.put(item.getVin(), vinDocument);
        }
    }
}
