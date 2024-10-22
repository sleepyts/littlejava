package com.test.Pool;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {"test.topic"},groupId = "my-group")
    public void testListener(ConsumerRecord<?,?> mes){
        log.info("offset:{} partition:{}",mes.offset(),mes.partition());
    }
}
