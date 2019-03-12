package com.haliri.israj.javaspringbootrabbitmq.message;

import com.haliri.israj.javaspringbootrabbitmq.config.RabbitBroadcastConfig;
import com.haliri.israj.javaspringbootrabbitmq.config.RabbitDirectConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = RabbitDirectConfig.DIRECT_QUEUE)
    @SendTo("reply-queue")
    public String direct(String message) {
        return "From DIRECT : " + message;
    }

    @RabbitListener(queues = RabbitBroadcastConfig.FANOUT_QUEUE1)
    @SendTo("reply-fanout1")
    public String orderFromFanout1(String message) {
        return "From FANOUT IS BROADCAST 1 : " + message;
    }

    @RabbitListener(queues = RabbitBroadcastConfig.FANOUT_QUEUE2)
    @SendTo("reply-fanout2")
    public String orderFromFanout2(String message) {
        return "From FANOUT IS BROADCAST 2 : " + message;
    }

    @RabbitListener(queues = RabbitBroadcastConfig.TOPIC_QUEUE1)
    @SendTo("reply-topic1")
    public String orderFromTopic1(String message) {
        return "From TOPIC 1 : " + message;
    }

    @RabbitListener(queues = RabbitBroadcastConfig.TOPIC_QUEUE2)
    @SendTo("reply-topic2")
    public String orderFromTopic2(String message) {
        return "From TOPIC 2 : " + message;
    }
}
