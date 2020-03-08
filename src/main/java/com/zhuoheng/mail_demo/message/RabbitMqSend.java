package com.zhuoheng.mail_demo.message;

import com.zhuoheng.mail_demo.mapper.BrokerMessageLogMapper;
import com.zhuoheng.mail_demo.service.MailService;
import com.zhuoheng.mail_demo.constant.Constants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: lizhuoheng
 * @Contact: lzh421@qq.com
 * @Date: 2020/3/8 下午 12:43
 */

@Component
public class RabbitMqSend {

    @Autowired
    BrokerMessageLogMapper brokerMessageLogMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MailService mailService;
    /**
     * 回调函数: confirm确认
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                //如果confirm返回成功 则进行更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                System.err.println("异常处理...");
            }
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     * @param username
     * @throws Exception
     */
    public void sendUsername(String username, String messageId) throws Exception {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend("mail-exchange00", "mail.ABC", username, correlationData);
    }
}
