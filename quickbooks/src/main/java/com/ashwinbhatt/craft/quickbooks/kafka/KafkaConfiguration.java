package com.ashwinbhatt.craft.quickbooks.kafka;

import com.ashwinbhatt.craft.commons.kafka.KafkaFetchService;
import com.ashwinbhatt.craft.commons.kafka.process.KafkaRequestProcessor;
import com.ashwinbhatt.craft.commons.kafka.process.exceptions.KafkaDataFetchException;
import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import com.ashwinbhatt.craft.quickbooks.services.ValidationService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class KafkaConfiguration {

    private final KafkaConsumer<String, AddressChangeKafkaEntity> kafkaConsumer;
    private final ValidationService validationService;

    @Value("${app.kafka.topic}")
    private String kafkaTopic;

    @Value("${app.kafka.poll.dur.ms}")
    private long kafkaPollDurationMilSec;

    @Value("${app.kafka.processthread.count}")
    private int processorThreadCount;

    public KafkaConfiguration(KafkaConsumer<String, AddressChangeKafkaEntity> kafkaConsumer, ValidationService validationService) {
        this.kafkaConsumer = kafkaConsumer;
        this.validationService = validationService;
    }

    @Bean
    public KafkaFetchService<String> getKafkaFetchService() throws KafkaDataFetchException {
        List<KafkaRequestProcessor<String>> kafkaRequestProcessors = new ArrayList<>();
        for(int i=0;i<processorThreadCount;i++) {
            QuickBooksKafkaProcessor<String> kafkaProcessor =
                    new QuickBooksKafkaProcessor<>(i, validationService);

            kafkaRequestProcessors.add(kafkaProcessor);
        }
        KafkaFetchService<String> kafkaFetchService = new KafkaFetchService<>();
        kafkaFetchService.startKafkaProcessor(kafkaRequestProcessors, kafkaConsumer, kafkaTopic, kafkaPollDurationMilSec);
        return kafkaFetchService;
    }



}
