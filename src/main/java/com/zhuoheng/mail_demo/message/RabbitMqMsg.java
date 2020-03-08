package com.zhuoheng.mail_demo.message;

import com.rabbitmq.client.Channel;
import com.zhuoheng.mail_demo.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 异步发送邮箱
 */
@Component
@Slf4j
public class RabbitMqMsg {

    @Autowired
    MailService mailService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "mail-queue", durable = "true"),
            exchange = @Exchange(value = "mail-exchange", durable = "true", type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "mail.*"
        )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.err.println("--------------------------------------");
        System.err.println("消费端Payload: " + message.getPayload());
        String username=(String)message.getPayload();
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag, false);
        try {
            mailService.sendMail(username);
        }catch (Exception e){
            e.getMessage();
        }
    }




}
