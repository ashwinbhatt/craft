package com.ashwinbhatt.craft.commons.kafka.process.parsers;

import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import org.apache.kafka.common.serialization.Deserializer;

public class KafkaEntityDeserialiser  implements Deserializer<AddressChangeKafkaEntity> {
    @Override
    public AddressChangeKafkaEntity deserialize(String topic, byte[] data) {
        String strData = new String(data);
        String[] arr = strData.split("|");
        return new AddressChangeKafkaEntity(arr[1], arr[2], arr[3], arr[4]);
    }
}
