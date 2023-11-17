package com.ashwinbhatt.craft.commons.kafka;

import com.ashwinbhatt.craft.commons.kafka.process.KafkaDataFetcher;
import com.ashwinbhatt.craft.commons.kafka.process.KafkaRequestProcessor;
import com.ashwinbhatt.craft.commons.kafka.process.KafkaRequestProcessorThreadPoolService;
import com.ashwinbhatt.craft.commons.kafka.process.exceptions.KafkaDataFetchException;
import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

public class KafkaFetchService<K> {

    public void startKafkaProcessor(List<KafkaRequestProcessor<K>> kafkaRequestProcessors, KafkaConsumer<K, AddressChangeKafkaEntity> kafkaConsumer, String kafkaTopic, Long kafkaPollDurationMilSec) throws KafkaDataFetchException {

        KafkaRequestProcessorThreadPoolService<K> kafkaRequestProcessorThreadPoolService = new KafkaRequestProcessorThreadPoolService<>(kafkaRequestProcessors);
        KafkaDataFetcher<K> kafkaDataFetcher = new KafkaDataFetcher<>(kafkaConsumer, kafkaTopic, kafkaRequestProcessorThreadPoolService, kafkaPollDurationMilSec);
        kafkaDataFetcher.run();
        Thread kafkaDataFetcherThread = new Thread(kafkaDataFetcher);
        kafkaDataFetcherThread.start();

    }

}
