package com.ashwinbhatt.craft.commons.kafka.process;

import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

public class KafkaDataFetcher<K> implements Runnable {

    private final KafkaConsumer<K, AddressChangeKafkaEntity> kafkaConsumer;
    private final String kafkaTopic;

    private final KafkaRequestProcessorThreadPoolService<K> kafkaRequestProcessorThreadPoolService;

    private final Duration kafkaPollDuration;

    public KafkaDataFetcher(KafkaConsumer<K, AddressChangeKafkaEntity> kafkaConsumer, String kafkaTopic, KafkaRequestProcessorThreadPoolService<K> kafkaRequestProcessorThreadPoolService, Long pollDurationMilSec) {
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaTopic = kafkaTopic;
        this.kafkaConsumer.subscribe(Collections.singleton(kafkaTopic));
        this.kafkaRequestProcessorThreadPoolService = kafkaRequestProcessorThreadPoolService;
        kafkaPollDuration = Duration.ofMillis(pollDurationMilSec);
    }

    @Override
    public void run() {

        while(true) {
            ConsumerRecords<K, AddressChangeKafkaEntity> polledConsumerRecord = kafkaConsumer.poll(kafkaPollDuration);
            if(polledConsumerRecord.isEmpty()) {
                continue;
            }
            polledConsumerRecord.forEach(record -> {
                    kafkaRequestProcessorThreadPoolService.submit(record.key(), record.value());
            });
        }
    }
}
