package com.ashwinbhatt.craft.commons.kafka.process.parsers;

import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

public class KafkaEntitySerialiser implements Serializer<AddressChangeKafkaEntity> {

    @Override
    public byte[] serialize(String topic, AddressChangeKafkaEntity data) {
        String dataStr = topic+"|"+data.getRequestId()+"|"+data.getRequestType()+"|"+data.getNewAddress();
        return dataStr.getBytes();
    }

    @Override
    public byte[] serialize(String topic, Headers headers, AddressChangeKafkaEntity data) {
        String dataStr = topic+"|"+data.getRequestId()+"|"+data.getRequestType()+"|"+data.getOrgId()+"|"+data.getNewAddress();
        return dataStr.getBytes();
    }
}
