package com.test.Stream;



import com.test.Config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

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