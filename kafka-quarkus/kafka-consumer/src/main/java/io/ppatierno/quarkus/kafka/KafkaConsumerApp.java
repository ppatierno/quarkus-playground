package io.ppatierno.quarkus.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class KafkaConsumerApp {

    private KafkaConsumer<String, String> consumer;

    void onStart(@Observes StartupEvent event) {

        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Collections.singleton("test"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("record = " + record);
            }
        }
    }
    
    void onStop(@Observes ShutdownEvent event) {
        consumer.close();
    }
}