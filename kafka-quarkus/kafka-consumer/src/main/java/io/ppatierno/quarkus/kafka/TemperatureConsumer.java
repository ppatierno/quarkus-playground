package io.ppatierno.quarkus.kafka;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class TemperatureConsumer {

    @Incoming("temperature")
    public void consume(int temperature) {
        System.out.println("temp = " + temperature);
    }
}