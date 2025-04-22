package org.example.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ReggieAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieAppApplication.class, args);
        log.info("SpringBoot项目启动...");
    }

}
