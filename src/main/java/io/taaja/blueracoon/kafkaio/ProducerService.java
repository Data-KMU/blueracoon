package io.taaja.blueracoon.kafkaio;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.taaja.blueracoon.CoordinatesToIDService;
import io.taaja.messaging.JacksonSerializer;
import io.taaja.messaging.Topics;
import io.taaja.models.generic.Coordinates;
import io.taaja.models.spatial.data.update.PartialUpdate;
import io.taaja.models.spatial.data.update.actuator.PositionUpdate;
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

    private static Producer<Long, PartialUpdate> kafkaProducer;

    @ConfigProperty(name = "kafka.bootstrap-servers")
    public String bootstrapServers = "46.101.136.244:9092";

    synchronized void onStart(@Observes StartupEvent ev) {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());

        this.kafkaProducer = new KafkaProducer(producerProperties, new LongSerializer(), new JacksonSerializer());
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("shutdown kafka");
        this.kafkaProducer.close();
    }


    public void publishCoordinatesFromVehicle(String vehicleUUID, Coordinates coordinates) {
        String areaUUID;
        try{
            //if purple tiger is offline
            areaUUID = coordinatesToIDService.encode(coordinates);
        }catch (Exception e){
            //.. use default
            areaUUID = "c56b3543-6853-4d86-a7bc-1cde673a5582";
            log.error("purple tiger cant be reached", e);

        }

        this.kafkaProducer.send(
            new ProducerRecord<>(
                    Topics.SPATIAL_EXTENSION_LIFE_DATA_TOPIC_PREFIX + areaUUID,
                new PartialUpdate(
                        vehicleUUID,
                        new PositionUpdate(coordinates)
                )
            )
        );

    }
}
