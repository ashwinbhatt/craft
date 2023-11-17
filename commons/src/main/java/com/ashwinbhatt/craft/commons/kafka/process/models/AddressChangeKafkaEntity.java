package com.ashwinbhatt.craft.commons.kafka.process.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressChangeKafkaEntity {

    private String requestId;
    private String requestType;
    private String orgId;
    private String newAddress;

}
