package com.ashwinbhatt.craft.commongs.kafka.mocks;

import com.ashwinbhatt.craft.commons.kafka.process.KafkaRequestProcessor;
import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MockKafkaRequestProcessor<K> extends KafkaRequestProcessor {

    @Getter
    List<AddressChangeKafkaEntity> fetchedData = new ArrayList<>();

    public MockKafkaRequestProcessor(int processorRunId) {
        super(processorRunId);
    }

    @Override
    public void doTask(Object key, AddressChangeKafkaEntity data) {
        fetchedData.add(data);
    }
}
