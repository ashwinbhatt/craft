package com.ashwinbhatt.craft.orgmanager.productvalidator;

import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

@Setter
public class SingleValidator implements Callable<Boolean> {

    private final String url;
    private final AddressChangeRequest addressChangeRequest;
    private final RestTemplate restTemplate;

    public SingleValidator(String url, AddressChangeRequest a, RestTemplate restTemplate) {
        this.url = url;
        this.addressChangeRequest = a;
        this.restTemplate = restTemplate;
    }

    @Override
    public Boolean call() throws Exception {
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(url, addressChangeRequest, Boolean.class);
        if(responseEntity.getStatusCode().is2xxSuccessful() && Boolean.TRUE.equals(responseEntity.getBody())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
