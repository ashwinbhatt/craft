package com.ashwinbhatt.craft.commongs.kafka;
import com.ashwinbhatt.craft.commongs.kafka.mocks.MockKafkaConsumer;
import com.ashwinbhatt.craft.commongs.kafka.mocks.MockKafkaEntity;
import com.ashwinbhatt.craft.commongs.kafka.mocks.MockKafkaRequestProcessor;
import com.ashwinbhatt.craft.commons.kafka.KafkaFetchService;
import com.ashwinbhatt.craft.commons.kafka.process.KafkaRequestProcessor;
import com.ashwinbhatt.craft.commons.kafka.process.exceptions.KafkaDataFetchException;
import com.ashwinbhatt.craft.commons.kafka.process.models.AddressChangeKafkaEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class KafkaProcessCommonTest {



    public void kafkaCommonTest(){

        ConsumerRecord<String, AddressChangeKafkaEntity> d1 = new ConsumerRecord<>("test", 0, 0, "1", new MockKafkaEntity("1", "1", "1"));
        ConsumerRecord<String, AddressChangeKafkaEntity> d2 = new ConsumerRecord<>("test", 0, 0, "2", new MockKafkaEntity("2", "2", "1"));
        ConsumerRecord<String, AddressChangeKafkaEntity> d3 = new ConsumerRecord<>("test", 0, 0, "3", new MockKafkaEntity("3", "3", "1"));
        ConsumerRecord<String, AddressChangeKafkaEntity> d4 = new ConsumerRecord<>("test", 0, 0, "4", new MockKafkaEntity("4", "4", "1"));

        Properties properties = new Properties();
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", MockKafkaEntity.class.getName());
        properties.put("bootstrap.servers", MockKafkaEntity.class.getName());

        MockKafkaConsumer<String, AddressChangeKafkaEntity> kafkaConsumer = new MockKafkaConsumer<>(properties);
        kafkaConsumer.insertData(d1);
        kafkaConsumer.insertData(d2);
        kafkaConsumer.insertData(d3);
        kafkaConsumer.insertData(d4);


        KafkaRequestProcessor<String> kafkaRequestProcessor1 = new MockKafkaRequestProcessor<>(1);
        KafkaRequestProcessor<String> kafkaRequestProcessor2 = new MockKafkaRequestProcessor<>(2);

        String topicName = "test";
        Long kafkaPllDurationMIlSec = 10 * 1000L;

        KafkaFetchService<String> kafkaFetchService = new KafkaFetchService<>();
        try {
            kafkaFetchService.startKafkaProcessor(Arrays.asList(kafkaRequestProcessor1, kafkaRequestProcessor2), kafkaConsumer, topicName, kafkaPllDurationMIlSec);
        } catch (KafkaDataFetchException e) {
            Assert.fail(e.getMessage());
        }

        List<MockKafkaEntity> lis1 = ((MockKafkaRequestProcessor)kafkaRequestProcessor1).getFetchedData();
        List<MockKafkaEntity> lis2 =  ((MockKafkaRequestProcessor)kafkaRequestProcessor2).getFetchedData();

        if(lis1.size() + lis2.size() != 4){
            Assert.fail("Data don't match!");
            return;
        }

    }

}
