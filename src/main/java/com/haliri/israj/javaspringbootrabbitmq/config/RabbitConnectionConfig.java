package com.haliri.israj.javaspringbootrabbitmq.config;

import lombok.Setter;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
@Setter
public class RabbitConnectionConfig {

    @Value("${rabbit.host}")
    private String rabbitHost;

    @Value("${rabbit.port}")
    private Integer rabbitPort;

    @Value("${rabbit.username}")
    private String rabbitUsername;

    @Value("${rabbit.password}")
    private String rabbitPassword;

    @Value("${rabbit.vhost}")
    private String rabbitVHost;

    @Bean()
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(StringMessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public StringMessageConverter StringMessageConverter() {
        return new StringMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitHost, rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        connectionFactory.setVirtualHost(rabbitVHost);
        return connectionFactory;
    }
}
