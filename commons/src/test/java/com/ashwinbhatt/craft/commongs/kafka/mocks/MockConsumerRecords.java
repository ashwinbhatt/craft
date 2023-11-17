package com.ashwinbhatt.craft.commongs.kafka.mocks;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;

public class MockConsumerRecords<K, V> extends ConsumerRecords {

    private List<ConsumerRecord<K, V>> data = new ArrayList<>();

    public MockConsumerRecords() {
        super(null);
    }

    public void injectData(ConsumerRecord<K, V> v) {
        data.add(v);
    }



    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public void forEach(Consumer action) {
        for(ConsumerRecord<K, V> entry: data) {
            action.accept(entry);
        }
    }
}
