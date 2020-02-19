package com.xxxx;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xxxx.crm.dao")
//@EnableScheduling
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
