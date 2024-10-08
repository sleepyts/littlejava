package com.test.Stream;

import com.test.Config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = RabbitMqConfig.CONSUME_QUEUE)
    public void receive(String message) {
        log.info("Received message: " + message);
    }
}
