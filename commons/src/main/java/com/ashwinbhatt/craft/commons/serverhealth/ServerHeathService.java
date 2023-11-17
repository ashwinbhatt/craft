package com.ashwinbhatt.craft.commons.serverhealth;

import com.ashwinbhatt.craft.commons.serverhealth.models.ServerRegisterRequest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ServerHeathService {

    private final RestTemplate restTemplate;
    private final String productType;
    private final String serverId;

    private static String REGISTER_URL = "http://localhost:8082/server/register";
    private static String PING_URL = "http://localhost:8081/gossip/ping?serverId=";


    public ServerHeathService(RestTemplate restTemplate, String productType) {
        this.restTemplate = restTemplate;
        this.productType = productType;
        this.serverId = productType;
    }


    public void register() {
        restTemplate.postForEntity(REGISTER_URL, new ServerRegisterRequest(serverId, productType), String.class);
    }

    public void ping() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PING_URL+serverId);

        restTemplate.exchange(
                builder.build().toUri(),
                HttpMethod.POST,
                null,
                String.class);

    }

}
