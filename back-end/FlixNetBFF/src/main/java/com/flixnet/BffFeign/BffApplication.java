package com.flixnet.BffFeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.flixnet"}) 
@EnableFeignClients
public class BffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

}
