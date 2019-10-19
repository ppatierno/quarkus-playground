package io.ppatierno.quarkus.kafka;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;

@ApplicationScoped
public class TemperatureGenerator {

    private Random random = new Random();

    @Outgoing("temperature")
    public Flowable<Integer> generate() {
        return Flowable.interval(5, TimeUnit.SECONDS)
                        .map(t ->  20 + random.nextInt(25 + 1 - 20));
    }
}