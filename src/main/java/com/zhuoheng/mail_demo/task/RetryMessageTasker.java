package com.zhuoheng.mail_demo.task;

import com.zhuoheng.mail_demo.bean.BrokerMessageLog;
import com.zhuoheng.mail_demo.mapper.BrokerMessageLogMapper;
import com.zhuoheng.mail_demo.message.RabbitMqSend;
import com.zhuoheng.mail_demo.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RetryMessageTasker {
    @Autowired
    private RabbitMqSend rabbitMqSend;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void reSend() {
        System.out.println("-----------定时任务开始-----------");
        //pull status = 0 and timeout message
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            if (messageLog.getTryCount() >= 3) {
                //update fail message
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            } else {
                // resend
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(), new Date());
                String username = (String)messageLog.getMessage();
                try {
                    rabbitMqSend.sendUsername(username,messageLog.getMessageId());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("-----------异常处理-----------");
                }
            }
        });
    }

}

