package com.ashwinbhatt.craft.payrolls;


import com.ashwinbhatt.craft.commons.serverhealth.ServerHeathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class PayrollApplication {

    private final ServerHeathService serverHeathService;

    public PayrollApplication(ServerHeathService serverHeathService) {
        this.serverHeathService = serverHeathService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PayrollApplication.class, args);
    }

    @Scheduled(fixedRate = 10000)
    public void yourScheduledMethod() {
        serverHeathService.ping();
    }
}
