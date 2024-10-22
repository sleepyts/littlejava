package com.test.Controller.Config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AllowedListDeserializingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    // 死信交换机名称
    public static final String DEAD_LETTER_EXCHANGE = "dlx.exchange";
    // 实际消费队列名称
    public static final String CONSUME_QUEUE = "consume.queue";
    // 延迟交换机名称
    public static final String DELAY_EXCHANGE = "delay.exchange";
    // 延迟队列名称
    public static final String DELAY_QUEUE = "delay.queue";
    // 延迟队列的路由键
    public static final String DELAY_ROUTING_KEY = "delay.routingkey";
    // 实际消费队列的路由键
    public static final String CONSUME_ROUTING_KEY = "consume.routingkey";
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE,true,false);
    }

    @Bean
    public Queue consumeQueue(){
        return new Queue(CONSUME_QUEUE);
    }

    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(DELAY_EXCHANGE)
                // 设置死信交换机
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                // 设置死信路由键
                .withArgument("x-dead-letter-routing-key", CONSUME_ROUTING_KEY)
                .withArgument("x-message-ttl", 2000)
                .build();
    }

    @Bean
    public DirectExchange delayExchange(){
        return  ExchangeBuilder.directExchange(DELAY_EXCHANGE).build();
    }

    @Bean
    public Binding consumeBinding(Queue consumeQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(consumeQueue).to(deadExchange).with(CONSUME_ROUTING_KEY);
    }
    @Bean
    public Binding delayBinding(Queue delayQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTING_KEY);
    }
}
