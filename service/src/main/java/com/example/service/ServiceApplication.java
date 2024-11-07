package com.example.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.io.InputStream;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    
    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

}


// cora iberkleid 
// @ciberkleid 

@Controller
@ResponseBody
class CoraDemo {

    private final RestClient http;

    CoraDemo(RestClient http) {
        this.http = http;
    }

    @GetMapping("/delay")
    String delay() {
        return this.http
                .get()
                .uri("https://httpbin.org/delay/5")
                .retrieve()
                .body(String.class);
    }

}


//@Component
class YouIncompleteMe implements ApplicationRunner {

    private final IncompleteEventPublications eventPublications;

    YouIncompleteMe(IncompleteEventPublications eventPublications) {
        this.eventPublications = eventPublications;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        this.eventPublications.resubmitIncompletePublications(new Predicate<EventPublication>() {
//            @Override
//            public boolean test(EventPublication eventPublication) {
//                LockRegistry lr; 
//                try (lr.tryAccquire("lockName" , TimeOut)){
//                    
//                }
//                finally {
//                    lr.release();
//                }
//                var identifier = eventPublication.getIdentifier();
//                return false;
//            }
//        }); 
    }
}
