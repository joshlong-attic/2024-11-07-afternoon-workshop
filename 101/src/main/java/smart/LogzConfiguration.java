package smart;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(MessageProperties.class)
@Configuration
class LogzConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Message message(MessageProperties properties) {
        return new Message(properties.message());
    }

    @Bean
    MyMessageController myMessageController(Message message) {
        return new MyMessageController(message);
    }
}

@ConfigurationProperties(prefix = "message")
record MessageProperties(String message) {
}