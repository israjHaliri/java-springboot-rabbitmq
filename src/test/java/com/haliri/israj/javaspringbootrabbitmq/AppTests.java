package com.haliri.israj.javaspringbootrabbitmq;

import com.haliri.israj.javaspringbootrabbitmq.config.RabbitBroadcastConfig;
import com.haliri.israj.javaspringbootrabbitmq.config.RabbitDirectConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        String resultDirect = (String) rabbitTemplate.convertSendAndReceive(RabbitDirectConfig.DIRECT_EXCHANGE, RabbitDirectConfig.DIRECT_QUEUE, "msg direct");

        String resultFanout1 = (String) rabbitTemplate.convertSendAndReceive(RabbitBroadcastConfig.FANOUT_EXCHANGE, "", "msg fanout 1");
        String resultFanout2 = (String) rabbitTemplate.convertSendAndReceive(RabbitBroadcastConfig.FANOUT_EXCHANGE, "", "msg fanout 2");
        String resultTopic1 = (String) rabbitTemplate.convertSendAndReceive(RabbitBroadcastConfig.TOPIC_EXCHANGE, "common.important.info", "msg topic 1");
        String resultTopic2 = (String) rabbitTemplate.convertSendAndReceive(RabbitBroadcastConfig.TOPIC_EXCHANGE, "user.error", "msg topic 2");


        System.out.println("----------- RESULT ----------");
        System.out.println(resultDirect);
        System.out.println(resultFanout1);
        System.out.println(resultFanout2);
        System.out.println(resultTopic1);
        System.out.println(resultTopic2);
        System.out.println("----------- RESULT ----------");
    }

}
