package com.zhuoheng.mail_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@MapperScan(basePackages = "com.zhuoheng.mail_demo.mapper")
@EnableScheduling
public class MailDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailDemoApplication.class, args);
    }


}
