package com.zhuoheng.mail_demo.service;

/**
 * @author lzhuoheng
 */
public interface MailService {

    /**
     * 发送邮件
     * @param username   邮件收件人
     */
    void sendMail(String username);

}
