KAFKA V 2.4

References
1. https://kafka.apache.org/documentation/
2. https://kafka.apache.org/24/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html
3. https://kafka.apache.org/24/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
4. https://kafka.apache.org/24/documentation/streams/tutorial
4. https://docs.spring.io/spring-kafka/reference/html/#preface

Run Unit Tests

 mvn clean test -Dtest=com.sibyg.lab.kafka_samples.commons.KafkaUtilsTest
 mvn clean test -Dtest=com.sibyg.lab.kafka_samples.commons.KafkaUtilsTest#shouldProduceAndConsume
 mvn clean test -Dtest=com.sibyg.lab.kafka_samples.commons.KafkaUtilsTest#shouldConsumerViaStream
 mvn clean test -Dtest=com.sibyg.lab.kafka_samples.commons.KafkaUtilsTest#shouldVerifyViaSpring
 
KAFKA V 0.10

References
1. https://docs.spring.io/autorepo/docs/spring-kafka-dist/1.1.7.BUILD-SNAPSHOT/reference/htmlsingle/

Compatibility

Apache Kafka 0.10.0.x or 0.10.1.x

NOTE
 
When using Spring Boot, omit the version and Boot will automatically bring in the correct version that is compatible with your Boot version:
 
Tested with Spring Framework version dependency is 4.3.7 but it is expected that the framework will work with earlier versions of Spring.
Annotation-based listeners require Spring Framework 4.1 or higher, however.
Minimum Java version: 7