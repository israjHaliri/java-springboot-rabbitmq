package com.haliri.israj.javaspringbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RabbitBroadcastConfig {
    public final static String FANOUT_QUEUE1 = "fanout-queue1";
    public final static String FANOUT_QUEUE2 = "fanout-queue2";
    public final static String FANOUT_EXCHANGE = "fanout-exchange";

    public final static String TOPIC_QUEUE1 = "topic-queue1";
    public final static String TOPIC_QUEUE2 = "topic-queue2";
    public final static String TOPIC_EXCHANGE = "topic-exchange";
    
    public final static String FANOUT_DEAD = "dead-fanout";
    public final static String TOPIC_DEAD = "dead-topic";

    @Bean
    Exchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Queue topicQueue() {
        return QueueBuilder.durable(TOPIC_QUEUE1)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", TOPIC_DEAD)
                .withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    Queue topicQueue2() {
        return QueueBuilder.durable(TOPIC_QUEUE2)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", TOPIC_DEAD)
                .withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    Binding bindingQTopic() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with("*.important.*").noargs();
    }

    @Bean
    Binding bindingQTopic2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("user.#").noargs();
    }

    @Bean
    public List<Declarable> fanoutBindings() {
        Queue fanoutQueue1 = new Queue(FANOUT_QUEUE1, false);
        Queue fanoutQueue2 = new Queue(FANOUT_QUEUE2, false);

        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE);
        return Arrays.asList(
                fanoutQueue1,
                fanoutQueue2,
                fanoutExchange,
                BindingBuilder.bind(fanoutQueue1).to(fanoutExchange),
                BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
    }

    @Bean
    public SimpleRabbitListenerContainerFactory broadcastContainer(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
