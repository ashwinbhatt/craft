package com.ashwinbhatt.craft.commongs.kafka.mocks;

import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import lombok.Getter;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class MockKafkaEntity extends AddressChangeKafkaEntity{

    @Getter
    private String data;

    public MockKafkaEntity() {

    }

    public MockKafkaEntity(String requestId, String requestType, String data) {
    }
}
