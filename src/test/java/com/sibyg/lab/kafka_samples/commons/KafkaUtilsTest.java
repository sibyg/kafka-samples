package com.sibyg.lab.kafka_samples.commons;

import static com.sibyg.lab.kafka_samples.commons.KafkaUtils.produceTo;
import static com.sibyg.lab.kafka_samples.commons.KafkaUtils.validateConsumption;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

public class KafkaUtilsTest {

    @Test
    public void shouldProduceAndConsume() {
        // when
        produceTo(KafkaUtils.TOPIC);

        // then
        validateConsumption(KafkaUtils.TOPIC);
    }


    @Test
    public void shouldConsumerViaStream() {
        // given
        produceTo(KafkaUtils.STREAM_SOURCE_TOPIC);
        // and build the topology
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream(KafkaUtils.STREAM_SOURCE_TOPIC).to(KafkaUtils.STEAM_SINK_TOPIC);
        Topology topology = builder.build();
        // and start the stream processing
        KafkaStreams streams = new KafkaStreams(topology, KafkaFactory.streamsConfig());
        streams.start();

        // then
        validateConsumption(KafkaUtils.STEAM_SINK_TOPIC);
    }

    @Test
    public void shouldVerifyViaSpring() throws Exception {
        // given
        String topic = "SPRING_TOPIC";

        // and
        ContainerProperties containerProps = new ContainerProperties(topic);
        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener((MessageListener<Integer, String>) message -> {
            System.out.printf("%n received: " + message);
            latch.countDown();
        });

        KafkaMessageListenerContainer<String, String> container = KafkaFactory.createContainer(containerProps);
        container.setBeanName("testAuto");
        container.start();

        Thread.sleep(1000); // wait a bit for the container to start
        KafkaTemplate<String, String> template = KafkaFactory.createTemplate();
        template.setDefaultTopic(topic);
        template.sendDefault("0", "foo");
        template.sendDefault("2", "bar");
        template.sendDefault("0", "baz");
        template.sendDefault("2", "qux");

        template.flush();
        assert (latch.await(10, TimeUnit.SECONDS));

        container.stop();

    }
}