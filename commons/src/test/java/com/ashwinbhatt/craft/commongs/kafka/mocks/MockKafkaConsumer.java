package com.ashwinbhatt.craft.commongs.kafka.mocks;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Properties;

public class MockKafkaConsumer<K, V> extends KafkaConsumer {

    Deque<ConsumerRecord<K, V>> queue = new ArrayDeque<>();

    public MockKafkaConsumer(Properties properties) {
        super(properties);
    }

    public void insertData(ConsumerRecord<K, V> data) {
        queue.addLast(data);
    }

    @Override
    public ConsumerRecords<K, V> poll(Duration kafkaPollDuration) {
        MockConsumerRecords<K, V> consumerRecords = new MockConsumerRecords();
        if(!queue.isEmpty()){
            consumerRecords.injectData(queue.pollFirst());
        }
        if(!queue.isEmpty()){
            consumerRecords.injectData(queue.pollFirst());
        }
        return consumerRecords;
    }



}
