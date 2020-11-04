package com.man.vin.truck.batch.file;

import com.man.vin.truck.batch.file.document.VinDocument;
import com.man.vin.truck.batch.file.repository.VinCodeRepository;
import com.man.vin.truck.batch.file.router.VinRouter;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(VinRouter.class)
@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class VinRouterTest {

    @Autowired
    private VinRouter router;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private Environment env;

    @Autowired
    static VinCodeRepository repository;

    @EndpointInject("{{router.to}}")
    private MockEndpoint mockEndpoint;
    
    @Autowired
    ApplicationContext context;

    @Test
    public void shouldSendAFullSetToDabtabase() throws InterruptedException {

        env.getDefaultProfiles()[0];

        context.containsBean("mockEndpoint");
        context.getBean("mockEndpoint");
        context.getClass();
        repository.getClass();

        producerTemplate.sendBodyAndHeader(env.getProperty("router.from"), getHardCodeBulk(),"CamelFileName", "hard_001.txt");
        producerTemplate.sendBodyAndHeader(env.getProperty("router.from"), getSoftCodeBulk(),"CamelFileName", "soft_001.txt");
        mockEndpoint.expectedMessageCount(2);
        mockEndpoint.assertIsSatisfied();

        verify(repository, times(2)).insert(isA(VinDocument.class));
        verify(repository, times(0)).save(isA(VinDocument.class));
    }

    @Test
    public void shouldHandleInvalidFileName() throws InterruptedException {
        producerTemplate.sendBodyAndHeader(env.getProperty("router.from"), getHardCodeBulk(),"CamelFileName", "out_of_pattern_001.txt");
        mockEndpoint.expectedMessageCount(0);
        mockEndpoint.assertIsSatisfied();

        verify(repository, times(0)).insert(isA(VinDocument.class));
        verify(repository, times(0)).save(isA(VinDocument.class));
    }

    public String getHardCodeBulk(){
        return "3C3CFFER4ET929645,6VO6Uq\n" +
                "3C3CFFER4ET929645,ZCLFOe\n" +
                "3C3CFFER4ET929645,jyP5PK\n" +
                "3C3CFFER4ET929645,ObZw28\n" +
                "3C3CFFER4ET929645,YxKjcX\n" +
                "3C3CFFER4ET929645,PWO7oa\n" +
                "3C3CFFER4ET929645,F73iHn\n" +
                "3C3CFFER4ET929645,fHBBOl\n" +
                "3C3CFFER4ET929645,qOhzQx\n" +
                "3C3CFFER4ET929645,Cqs4tI\n" +
                "3C3CFFER4ET929645,7aKXf9\n" +
                "3C3CFFER4ET929645,wLxakm\n" +
                "3C3CFFER4ET929645,LzWJXu\n" +
                "3C3CFFER4ET929645,TJrzSC\n" +
                "3C3CFFER4ET929645,K343pO\n";
    }

    public String getSoftCodeBulk(){
        return "3C3CFFER4ET929645,FhFXVE\n" +
                "3C3CFFER4ET929645,FVlp0N\n" +
                "3C3CFFER4ET929645,I25pUg\n" +
                "3C3CFFER4ET929645,PeQWGL\n" +
                "3C3CFFER4ET929645,LYZzKL\n" +
                "3C3CFFER4ET929645,Cd9t6T\n" +
                "3C3CFFER4ET929645,pYgxjp\n" +
                "3C3CFFER4ET929645,T55Adn\n" +
                "3C3CFFER4ET929645,cjKv9N\n" +
                "3C3CFFER4ET929645,T2WuvF\n" +
                "3C3CFFER4ET929645,iBitD7\n" +
                "3C3CFFER4ET929645,3KAKvP\n" +
                "3C3CFFER4ET929645,XmUjoD\n" +
                "3C3CFFER4ET929645,s7ooQ9\n" +
                "3C3CFFER4ET929645,3SQwd2\n";
    }

    @TestConfiguration
    public static class TestConfig {
        @Bean
        @Primary
        public VinCodeRepository repository(){
            return Mockito.spy(VinCodeRepository.class);
        };

    }

}
