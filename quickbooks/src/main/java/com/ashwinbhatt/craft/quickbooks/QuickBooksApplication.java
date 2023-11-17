package com.ashwinbhatt.craft.quickbooks;

import com.ashwinbhatt.craft.commons.serverhealth.ServerHeathService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class QuickBooksApplication {

    private final ServerHeathService serverHeathService;

    public QuickBooksApplication(ServerHeathService serverHeathService) {
        this.serverHeathService = serverHeathService;
    }

    public static void main(String[] args) {
        SpringApplication.run(QuickBooksApplication.class, args);
    }

    @Scheduled(fixedRate = 10000)
    public void ping() {
        serverHeathService.ping();
    }
}
