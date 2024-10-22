package com.test.Config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic testTopic(){
        return TopicBuilder
                .name("test.topic")
                .partitions(2)
                .build();
    }
}
