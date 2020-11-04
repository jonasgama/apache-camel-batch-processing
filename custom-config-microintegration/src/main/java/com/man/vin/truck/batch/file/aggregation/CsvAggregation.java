package com.man.vin.truck.batch.file.aggregation;

import com.man.vin.truck.batch.file.dataformat.CsvItem;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CsvAggregation implements AggregationStrategy {

        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            CsvItem item = newExchange.getIn().getBody(CsvItem.class);
            String fileName = newExchange.getIn().getHeader("CamelFileName").toString();
            if(!fileName.contains("hard") && !fileName.contains("soft")){
                return null;
            }
            item.setFileName(fileName);
            List<CsvItem> items = new ArrayList<>();
            if (oldExchange == null) {
                items.add(item);
                newExchange.getIn().setBody(items);
                return newExchange;
            } else {
                items = oldExchange.getIn().getBody(List.class);
                items.add(item);
                oldExchange.getIn().setBody(items);
                return oldExchange;
            }
        }

    }
