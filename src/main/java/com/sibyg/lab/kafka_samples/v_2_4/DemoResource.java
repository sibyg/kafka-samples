package com.sibyg.lab.kafka_samples.v_2_4;

import com.sibyg.lab.kafka_samples.commons.KafkaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoResource {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) throws InterruptedException {

        String topic = "DEMO_CAB_TOPIC";

        KafkaUtils.produceTo(topic, "hello", name);

        return KafkaUtils.validateConsumption(topic);
    }
}
