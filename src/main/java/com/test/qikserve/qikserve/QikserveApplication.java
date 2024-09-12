package com.test.qikserve.qikserve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@Slf4j
public class QikserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(QikserveApplication.class, args);
    }


}
