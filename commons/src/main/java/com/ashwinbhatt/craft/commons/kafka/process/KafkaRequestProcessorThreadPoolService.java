package com.ashwinbhatt.craft.commons.kafka.process;

import com.ashwinbhatt.craft.commons.kafka.process.exceptions.KafkaDataFetchException;
import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KafkaRequestProcessorThreadPoolService<K> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaRequestProcessorThreadPoolService.class);

    private final int threadPoolSize;
    private final ExecutorService executorService;
    private final Set<Future<Integer>> kafkaRequestProcessorSet;
    private final Map<Integer, KafkaRequestProcessor<K>> requestProcessorMap;


    public KafkaRequestProcessorThreadPoolService(List<KafkaRequestProcessor<K>> kafkaRequestProcessors) throws KafkaDataFetchException {
        if(kafkaRequestProcessors == null || kafkaRequestProcessors.isEmpty()) {
            throw new KafkaDataFetchException("Provided kafka request processor list is null or empty, it should be of length > 0.");
        }
        this.threadPoolSize = kafkaRequestProcessors.size();
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        kafkaRequestProcessorSet = new HashSet<>();
        requestProcessorMap = new HashMap<>();
        kafkaRequestProcessors.forEach(kafkaRequestProcessor -> requestProcessorMap.put(kafkaRequestProcessor.getProcessorRunId(), kafkaRequestProcessor));
    }

    public void submit(K key, AddressChangeKafkaEntity data) {

        // check if threadpool has threadPoolsize threads
        if(kafkaRequestProcessorSet.size() < threadPoolSize) {
            // just create a new thread and submit to pool
            for(KafkaRequestProcessor<K> kafkaRequestProcessor : requestProcessorMap.values()){
                if(kafkaRequestProcessor.isWorking()) {
                    continue;
                }
                prepareKafkaRequestProcessorAndSubmit(kafkaRequestProcessor, key, data);
            }
            return;
        }

        while(true) {

            for(Future<Integer> future : kafkaRequestProcessorSet) {
                if(future.isDone()) {
                    try {
                        prepareKafkaRequestProcessorAndSubmit(requestProcessorMap.get(future.get()), key, data);
                        return;
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage());
                    }
                }
            }

        }

    }

    public void prepareKafkaRequestProcessorAndSubmit(KafkaRequestProcessor<K> kafkaRequestProcessor, K key, AddressChangeKafkaEntity data) {
        kafkaRequestProcessor.setKey(key);
        kafkaRequestProcessor.setData(data);
        Future<Integer> future = executorService.submit(kafkaRequestProcessor);
        kafkaRequestProcessorSet.add(future);
    }
}
