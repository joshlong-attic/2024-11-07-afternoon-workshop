package com.example.service.adoptions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.aot.generate.GenerationContext;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.aot.BeanFactoryInitializationCode;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;

@Configuration
//@RegisterReflectionForBinding(DogAdoptionSuggestion.class)
class Assistant {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder,
                          DogRepository dogRepository,
                          VectorStore vectorStore
    ) {
        if (false)
            dogRepository.findAll().forEach(dog -> {
                var str = "id: %s, name: %s, description: %s".formatted(dog.id(), dog.name(), dog.description());
                var dogument = new Document(str);
                vectorStore.add(List.of(dogument));
            });

        var system = """
                You are an AI powered assistant to help people adopt a dog from the adoption\s
                agency named Pooch Palace with locations in Amsterdam, Seoul, Tokyo, Singapore, Paris,\s
                Mumbai, New Delhi, Barcelona, San Francisco, and London. Information about the dogs available\s
                will be presented below. If there is no information, then return a polite response suggesting we\s
                don't have any dogs available.
                """;
        return builder
                .defaultSystem(system)
                // RETRIEVAL AUGMENTED GENERATION (RAG)
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

//    Executor yuck = Executors.newCachedThreadPool();
//    Executor yay = Executors.newVirtualThreadPerTaskExecutor();
//    


    @Bean
    ApplicationRunner assistantRunner(ChatClient chatClient) {
        return args -> {
            var content = chatClient
                    .prompt("do you have any neurotic dogs?")
                    .call()
                    .entity(DogAdoptionSuggestion.class);
            System.out.println("reply [" + content + "]");

        };
    }
}

@Configuration
class MyConfig {

    @Bean
    static MyBFIAP myBFIAP() {
        return new MyBFIAP();
    }
}

class MyBFIAP implements BeanFactoryInitializationAotProcessor {

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(ConfigurableListableBeanFactory beanFactory) {

        // 1. ingest
        // 2. bean definition
        // 3. 

        var setOfBeans = new HashSet<String>();

        return new BeanFactoryInitializationAotContribution() {
            @Override
            public void applyTo(GenerationContext generationContext, BeanFactoryInitializationCode code) {

                RuntimeHints runtimeHints = generationContext.getRuntimeHints();
                runtimeHints.reflection().registerType(DogAdoptionSuggestion.class);


                code.getMethods()
                        .add("adyenMethod", builder -> builder
                                .addCode("""
                                        System.out.println("hello, world"); 
                                        """));


            }
        };
    }
}

record DogAdoptionSuggestion(int id, String name, String description) {
}