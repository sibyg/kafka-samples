package com.sibyg.lab.kafka_samples.commons;

import static com.sibyg.lab.kafka_samples.commons.KafkaUtils.LISTENER_TOPIC;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class Listener {

    public final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = LISTENER_TOPIC)
    public void listen(ConsumerRecord<?, ?> cr) {
        System.out.println("AUTOWIRED LISTENER=" + cr.toString());
        latch1.countDown();
    }

    @KafkaListener(topics = KafkaUtils.STEAM_SINK_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void listenStream(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {

        System.out.println("AUTOWIRED LISTENER (STREAM): " + consumerRecord.value());

        ack.acknowledge();
        latch1.countDown();
    }
}