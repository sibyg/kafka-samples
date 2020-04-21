package com.sibyg.lab.kafka_samples.commons;

import java.time.Duration;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaUtils {

    public static final String TOPIC = "MY-TOPIC";
    public static final String STREAM_SOURCE_TOPIC = "STREAM_SOURCE_TOPIC";
    public static final String STEAM_SINK_TOPIC = "STREAM_SOURCE_TOPIC";
    public static final String LISTENER_TOPIC = "LISTENER_TOPIC";
    public static final KafkaProducer PRODUCER = KafkaFactory.producer();
    public static KafkaConsumer CONSUMER = KafkaFactory.consumer();

    public static void produceTo(String topic) {
        System.out.printf("%n%nProducing to TOPIC=" + topic);
        for (int i = 1; i < 6; i++) {
            String key = Integer.toString(i);
            String value = Integer.toString(i);
            PRODUCER.send(new ProducerRecord<>(topic, key, value));
            System.out.printf("%nPRODUCED=Key=%s, Value=%s", key, value);
        }
        PRODUCER.close();
    }

    public static void produceTo(String topic, String key, String value) {
        System.out.printf("%n%nProducing to TOPIC=" + topic);
        PRODUCER.send(new ProducerRecord<String, String>(topic, key, value));
//        PRODUCER.close();
    }

    public static String validateConsumption(String topic) {
        System.out.printf("%n%nConsuming from TOPIC=" + topic);
        CONSUMER.subscribe(Collections.singletonList(topic));

        StringBuilder returnValue = new StringBuilder("RECORDS:");
        while (true) {
            ConsumerRecords<String, String> records = CONSUMER.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                returnValue.append("RECORD=").append(record.toString());
                System.out.printf("%nCONSUMED=offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }


            if (!records.isEmpty()) {
                break;
            }
        }

        return returnValue.toString();
    }
}
