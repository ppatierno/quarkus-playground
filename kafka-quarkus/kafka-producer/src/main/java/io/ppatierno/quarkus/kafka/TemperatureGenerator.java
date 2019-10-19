package io.ppatierno.quarkus.kafka;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;

@ApplicationScoped
public class TemperatureGenerator {

    @ConfigProperty(name = "temperature.min")
    private int minTemperature;

    @ConfigProperty(name = "temperature.max")
    private int maxTemperature;

    private Random random = new Random();

    @Outgoing("temperature")
    public Flowable<Integer> generate() {
        return Flowable.interval(5, TimeUnit.SECONDS)
                        .map(t ->  this.minTemperature + random.nextInt(this.maxTemperature + 1 - this.minTemperature));
    }
}