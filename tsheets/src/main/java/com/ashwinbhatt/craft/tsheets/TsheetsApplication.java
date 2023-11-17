package com.ashwinbhatt.craft.tsheets;

import com.ashwinbhatt.craft.commons.serverhealth.ServerHeathService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class TsheetsApplication {

    private final ServerHeathService serverHeathService;

    public TsheetsApplication(ServerHeathService serverHeathService) {
        this.serverHeathService = serverHeathService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TsheetsApplication.class, args);
    }

    @Scheduled(fixedRate = 10000)
    public void yourScheduledMethod() {
        serverHeathService.ping();
    }
}
