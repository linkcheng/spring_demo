package cn.xyf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafkaTopicConfiguration {
    private KafkaTopicProperties properties;

    @Autowired
    public void setProperties(KafkaTopicProperties properties) {
        this.properties = properties;
    }

    @Bean
    public String topicGroupId() {
        return properties.getGroupId();
    }

    @Bean
    public String[] kafkaTopicName() {
        return properties.getTopicName();
    }
}
