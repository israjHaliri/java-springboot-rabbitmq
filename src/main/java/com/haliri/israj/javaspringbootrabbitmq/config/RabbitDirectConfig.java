package com.haliri.israj.javaspringbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitDirectConfig implements RabbitListenerConfigurer {

    private final MessageHandlerMethodFactory messageHandlerMethodFactory;

    public static final String DIRECT_QUEUE = "direct-queue";
    public static final String DIRECT_EXCHANGE = "direct-exchange";
    private static final String DIRECT_DEAD_LETTERS_ROUTING_KEY = "dead-direct";
    private static final String DIRECT_DEAD_LETTERS_QUEUE = "dead-direct";

    @Autowired
    public RabbitDirectConfig(MessageHandlerMethodFactory messageHandlerMethodFactory) {
        this.messageHandlerMethodFactory = messageHandlerMethodFactory;
    }

    @Bean
    public Exchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(DIRECT_QUEUE)
                .withArgument("x-dead-letter-exchange", DIRECT_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DIRECT_DEAD_LETTERS_ROUTING_KEY)
                .withArgument("x-message-ttl", 60000)
                .build();
    }

    @Bean
    public Binding bindingdirectQueueToExchange() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_QUEUE).noargs();
    }

    @Bean
    public Queue deadLetterdirectQueue() {
        return QueueBuilder.durable(DIRECT_DEAD_LETTERS_QUEUE).build();
    }

    @Bean
    public Binding bindingDeadLetterdirectQueueToExchange() {
        return BindingBuilder.bind(deadLetterdirectQueue()).to(directExchange()).with(DIRECT_DEAD_LETTERS_QUEUE).noargs();
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }
}