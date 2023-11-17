package com.ashwinbhatt.craft.payrolls;


import com.ashwinbhatt.craft.commons.serverhealth.ServerHeathService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppHealthConfiguration {

    private final RestTemplate restTemplate;

    public AppHealthConfiguration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    public ServerHeathService getServerHealthService() {
        ServerHeathService heathService = new ServerHeathService(restTemplate, "payrolls");

        heathService.register();
        return heathService;
    }
}
