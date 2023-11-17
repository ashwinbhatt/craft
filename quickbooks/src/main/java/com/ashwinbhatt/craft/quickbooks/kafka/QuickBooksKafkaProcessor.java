package com.ashwinbhatt.craft.quickbooks.kafka;

import com.ashwinbhatt.craft.commons.kafka.process.KafkaRequestProcessor;
import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import com.ashwinbhatt.craft.quickbooks.services.ValidationService;


public class QuickBooksKafkaProcessor<String> extends KafkaRequestProcessor {

    private final ValidationService validationService;

    public QuickBooksKafkaProcessor(int processorRunId, ValidationService validationService) {
        super(processorRunId);
        this.validationService = validationService;
    }

    @Override
    public void doTask(Object key, AddressChangeKafkaEntity data) {

    }
}
