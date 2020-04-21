package com.sibyg.lab.kafka_samples.v_2_4;

import static com.sibyg.lab.kafka_samples.commons.KafkaUtils.LISTENER_TOPIC;
import static com.sibyg.lab.kafka_samples.commons.KafkaUtils.produceTo;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import com.sibyg.lab.kafka_samples.commons.KafkaUtils;
import com.sibyg.lab.kafka_samples.commons.Listener;
import org.apache.kafka.streams.KafkaStreams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(classes = Application.class)
public class SpringKafkaSampleTests {

    @Autowired
    private Listener listener;

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    KafkaStreams kafkaStreams;

    @Test
    public void testSimple() throws InterruptedException {
        template.send(LISTENER_TOPIC, "0", "foo");
        template.flush();
        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void testStreams() throws InterruptedException {
        // given
        produceTo(KafkaUtils.STREAM_SOURCE_TOPIC);

        kafkaStreams.start();

        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }
}
