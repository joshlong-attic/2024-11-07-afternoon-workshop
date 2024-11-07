package com.example.demo;

import org.springframework.context.annotation.Bean;
import smart.Logged;
import smart.Message;
import smart.TudorComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        ApplicationContext ac = SpringApplication.run(DemoApplication.class, args);
        MyComponent bean = ac.getBean(MyComponent.class);
        bean.foo();
    }
}

@Configuration
@ComponentScan
class AppConfig {
//    
//    @Bean
//    Message userProvidedMessage() {
//        return new Message("hello user provided message");
//    }
    
}

@Logged
@TudorComponent
class MyComponent {

    public void foo() {
        System.out.println("foo");
    }
}

