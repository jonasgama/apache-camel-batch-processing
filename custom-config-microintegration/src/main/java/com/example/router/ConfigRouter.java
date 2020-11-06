package com.example.router;

import com.example.aggregation.CsvAggregation;
import com.example.process.PersistProcess;
import com.example.dataformat.CsvItem;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ConfigRouter extends RouteBuilder {

    private DataFormat bindy;

    @Autowired
    private CsvAggregation csvAggregation;

    @Autowired
    private PersistProcess persistProcess;

    @Value("${required.size.to.persist}")
    public Integer requiredSize;

    public ConfigRouter(){
        bindy = new BindyCsvDataFormat(CsvItem.class);
    }

    @Override
    public void configure() throws Exception {
        this.onExcetions();
        this.routing();
    }

    private void routing(){
        from("{{router.from}}")
                .split(body().tokenize("\n")).stopOnException()
                .streaming().stopOnException()
                .unmarshal(bindy)
                .aggregate(constant(true),
                        csvAggregation)
                .completionSize(requiredSize)
                .completionTimeout(5000)
                .process(persistProcess)
                .marshal().json(JsonLibrary.Gson, CsvItem.class)
                .to("{{router.to}}");
    }

    private void onExcetions(){
        onException(Exception.class)
                .handled(true)
                .maximumRedeliveries(3)
                .onRedelivery(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Object camelExceptionCaught = exchange.getProperty("CamelExceptionCaught");
                        exchange.getIn().setBody(camelExceptionCaught);
                    }
                })
                .redeliveryDelay(5000)
                .log(LoggingLevel.ERROR,
                "An Error has been found on file: ${in.header.CamelFileName}: ${body}");
    }


}
