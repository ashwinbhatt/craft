package com.ashwinbhatt.craft.commons.kafka.process;

import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;

@Getter
public abstract class KafkaRequestProcessor<K> implements Callable<Integer> {

    private final Integer processorRunId;
    @Setter
    private K key;
    @Setter
    private AddressChangeKafkaEntity data;
    private boolean isWorking;

    public KafkaRequestProcessor(int processorRunId) {
        isWorking = false;
        this.processorRunId = processorRunId;
    }

    @Override
    public Integer call() throws Exception {
        isWorking = true;
        doTask(key, data);
        isWorking = false;
        return processorRunId;
    }

    public abstract void doTask(K key, AddressChangeKafkaEntity data);
}
