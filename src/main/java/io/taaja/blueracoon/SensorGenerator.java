package io.taaja.blueracoon;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.SubscriberWrapper;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.taaja.blueracoon.model.SensorStatus;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

/*
Generiert alle 10 Sekunden ein Json mit einer RandomID
Diese Json wird als Kafka Topic "Sensors" gesendet (in application.properties konfiguriert)
Zweck: zum Testen, ob die Daten aus den eingehenden Jsons verarbeitet werden
 */

@ApplicationScoped
@JBossLog
public class SensorGenerator {


    @Outgoing("generated-sensor")
    public Flowable<SensorStatus> generate() {
        log.debug("generate drone data");
        SensorStatus testStatus = new SensorStatus();
        testStatus.setId(UUID.randomUUID().toString());
        testStatus.setChannel("ch1");

        return Flowable.interval(5, TimeUnit.SECONDS).map(tick -> testStatus);
   }

}
