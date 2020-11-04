package com.man.vin.truck.batch.file.aggregation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.man.vin.truck.batch.file.dataformat.CsvItem;
import com.man.vin.truck.batch.file.document.VinDocument;
import org.apache.camel.EndpointInject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@RunWith(CamelSpringBootRunner.class)
public class CsvAggregationTest extends CamelTestSupport {

    @EndpointInject("mock:result")
    private MockEndpoint endpoint;

    @EndpointInject("mock:error")
    private MockEndpoint endpointFailure;

    @Test
    public void shouldSendMatchingMessage() throws Exception {
        Gson gson; gson = new Gson();
        String input = "3C3CFFER4ET929645,6VO6Uq";

        String headerName = "CamelFileName";
        String headerValue = "001_hard.txt";

        List<CsvItem> items = new ArrayList<>();

        CsvItem item = new CsvItem();
        item.setFileName(headerValue);
        item.setVin("3C3CFFER4ET929645");
        item.setCode("6VO6Uq");

        items.add(item);

        endpoint.expectedMessageCount(1);
        endpoint.expectedHeaderReceived(headerName,headerValue);
        endpoint.expectedBodiesReceived(gson.toJson(items));
        endpointFailure.expectedMessageCount(0);

        template.sendBodyAndHeader("direct:start", input, headerName, headerValue);

        endpoint.assertIsSatisfied();
        endpointFailure.assertIsSatisfied();
    }

    @Test
    public void shouldHandleInvalidBody() throws Exception {
        String input = ",,,,,,";

        String headerName = "CamelFileName";
        String headerValue = "001_hard.txt";

        endpoint.expectedMessageCount(0);
        endpointFailure.expectedMessageCount(1);
        template.sendBodyAndHeader("direct:start", input, headerName, headerValue);

        endpoint.assertIsSatisfied();

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                onException(Exception.class)
                        .handled(true)
                        .log(LoggingLevel.ERROR,
                                "An Error has been found on file: ${in.header.CamelFileName}")
                        .to("mock:error");

                from("direct:start")
                        .split(body()
                        .tokenize("\n"))
                        .unmarshal(new BindyCsvDataFormat(CsvItem.class) )
                        .aggregate(constant(true), new CsvAggregation())
                        .completionSize(1)
                        .marshal().json(JsonLibrary.Gson, CsvItem.class)
                        .log("result: ${headers} ${body}")
                        .to("mock:result");
            }
        };
    }

}
