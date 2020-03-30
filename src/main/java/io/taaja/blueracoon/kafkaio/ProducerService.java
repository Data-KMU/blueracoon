package io.taaja.blueracoon.kafkaio;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.CoordinatesToIDService;
import io.taaja.models.kafka.update.actuator.PositionUpdate;
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

    private static Producer<Long, PositionUpdate> kafkaProducer;

    @ConfigProperty(name = "kafka.bootstrap-servers")
    public String bootstrapServers = "46.101.136.244:9092";

    synchronized void onStart(@Observes StartupEvent ev) {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());

        this.kafkaProducer = new KafkaProducer<Long, PositionUpdate>(producerProperties, new LongSerializer(), new PositionSerializer());
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("shutdown kafka");
        this.kafkaProducer.close();
    }



    public void publishCoordinates(PositionUpdate positionUpdate){
        String uui;
        try{
            //if purple tiger is offline
            uui = coordinatesToIDService.encode(positionUpdate.getPosition());
        }catch (Exception e){
            //.. use default
            uui = "c56b3543-6853-4d86-a7bc-1cde673a5582";
            log.error("purple tiger cant be reached", e);

        }

        this.kafkaProducer.send(
            new ProducerRecord<>(
                    "vehicle-data-" + uui,
                    positionUpdate
            )
        );
    }
}
