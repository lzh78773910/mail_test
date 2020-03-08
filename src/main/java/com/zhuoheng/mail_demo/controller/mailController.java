package com.zhuoheng.mail_demo.controller;

import com.zhuoheng.mail_demo.bean.BrokerMessageLog;
import com.zhuoheng.mail_demo.mapper.BrokerMessageLogMapper;
import com.zhuoheng.mail_demo.message.RabbitMqSend;
import com.zhuoheng.mail_demo.service.MailService;
import com.zhuoheng.mail_demo.utils.DateUtils;
import com.zhuoheng.mail_demo.constant.Constants;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: lizhuoheng
 * @Contact: lzh421@qq.com
 * @Date: 2020/3/7 下午 6:51
 */
@RestController
public class mailController {

    @Autowired
    MailService mailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    BrokerMessageLogMapper brokerMessageLogMapper;


    @Autowired
    private RabbitMqSend rabbitMqSend;

    @GetMapping(value = "/sendCode")
    public String sendCode(String username) {
        mailService.sendMail(username);
        return "同步发送邮件";
    }

    @GetMapping(value = "/sendCodeByMq")
    public String sendCodeByMq(String username) {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("mail-exchange", "mail.abc", username, correlationData);
        return "异步发送邮件";
    }

    @GetMapping(value = "/sendCodeByMq2")
    public String sendCodeByMq2(String username) throws Exception{
        CorrelationData correlationData = new CorrelationData();
        String massageId= System.currentTimeMillis() + "$" + UUID.randomUUID().toString();
        correlationData.setId(massageId);
        rabbitTemplate.convertAndSend("mail-exchange", "mail.abc", username, correlationData);


        // 使用当前时间当做订单创建时间（为了模拟一下简化）
        Date orderTime = new Date();

        // 插入消息记录表数据
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息唯一ID
        brokerMessageLog.setMessageId(massageId);
        // 保存消息整体 转为JSON 格式存储入库
        brokerMessageLog.setMessage(username);
        // 设置消息状态为0 表示发送中
        brokerMessageLog.setStatus("0");
        // 设置消息未确认超时时间窗口为 一分钟
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateTime(new Date());
        brokerMessageLog.setUpdateTime(new Date());
        brokerMessageLogMapper.insertSelective(brokerMessageLog);
        // 发送消息
        rabbitMqSend.sendUsername(username,massageId);
        return "异步发送邮件 保证信息高投递性";
    }
}
