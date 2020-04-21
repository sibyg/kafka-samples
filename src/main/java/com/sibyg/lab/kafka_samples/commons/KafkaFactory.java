package com.sibyg.lab.kafka_samples.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

public class KafkaFactory {

    public static KafkaMessageListenerContainer<String, String> createContainer(ContainerProperties containerProps) {
        ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<String, String>(consumerProps());
        return new KafkaMessageListenerContainer<String, String>(cf, containerProps);
    }

    public static KafkaTemplate<String, String> createTemplate() {
        ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<String, String>(producerProps());
        return new KafkaTemplate<>(pf);
    }


    public static KafkaProducer producer() {
        return new KafkaProducer<>(producerProps());
    }

    private static Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
        return props;
    }

    public static KafkaConsumer consumer() {
        return new KafkaConsumer<>(consumerProps());
    }

    private static Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("session.timeout.ms", "30000");
        return props;
    }

    public static Properties streamsConfig() {
        Properties properties = new Properties();
        properties.putAll(streamsProps());
        return properties;
    }

    public static Map<String, Object> streamsProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // Default serde for keys of data records (here: built-in serde for String type)
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // Default serde for values of data records (here: built-in serde for Long type)
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return props;
    }
}
