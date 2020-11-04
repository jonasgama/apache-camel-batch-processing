package com.example.process;

import com.example.entity.ConfigEntity;
import com.man.vin.truck.batch.file.document.VinDocument;
import com.example.dataformat.CsvItem;
import com.example.service.ConfigService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersistProcess implements Processor {

    @Autowired
    private ConfigService service;

    @Override
    public void process(Exchange exchange) throws Exception {
        List<CsvItem> bulk= exchange.getIn().getBody(List.class);

        for (CsvItem item: bulk) {
            ConfigEntity configEntity = new ConfigEntity(item.getCode(), item.getConfiguration());
            service.save(configEntity);
        }

    }
}
