package com.example.studenrabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StudentRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentRabbitApplication.class, args);
    }

}
