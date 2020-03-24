package io.taaja.blueracoon.kafkaio;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.CoordinatesToIDService;
import io.taaja.blueracoon.model.Coordinates;
import lombok.extern.jbosslog.JBossLog;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Properties;
import java.util.UUID;

@ApplicationScoped
@JBossLog
public class ProducerService {

    @Inject
    CoordinatesToIDService coordinatesToIDService;

    private static Producer<Long, Coordinates> kafkaProducer;

    @ConfigProperty(name = "kafka.bootstrap-servers")
    public String bootstrapServers = "46.101.136.244:9092";

    synchronized void onStart(@Observes StartupEvent ev) {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());

        this.kafkaProducer = new KafkaProducer<Long, Coordinates>(producerProperties, new LongSerializer(), new CoordinateSerializer());
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("shutdown kafka");
        this.kafkaProducer.close();
    }



    public void publishCoordinates(Coordinates coordinates){
        this.kafkaProducer.send(
            new ProducerRecord<>(
                    "vehicle-data-" + coordinatesToIDService.encode(coordinates),
                    coordinates
            )
        );
    }
}
