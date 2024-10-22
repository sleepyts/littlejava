package com.test.Stream;



import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.test.Config.RabbitMqConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    public void produce(String message){
        for (int i=0 ;i < 1000;i++){
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.DELAY_EXCHANGE,
                    RabbitMqConfig.DELAY_ROUTING_KEY,
                    UUID.randomUUID());

        }

    }
}