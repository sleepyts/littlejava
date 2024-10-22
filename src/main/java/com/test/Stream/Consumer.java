package com.test.Stream;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.test.Config.RabbitMqConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = RabbitMqConfig.CONSUME_QUEUE)
    public void receive(String message) {
        log.info("Received message: " + message);
    }
}
