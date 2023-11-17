package com.ashwinbhatt.craft.mockkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MockKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockKafkaApplication.class, args);
    }
}
