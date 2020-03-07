package io.taaja.blueracoon;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.reactivex.Flowable;

/*
Generiert alle 10 Sekunden ein Json mit einer RandomID
Diese Json wird als Kafka Topic "Sensors" gesendet (in application.properties konfiguriert)
Zweck: zum Testen, ob die Daten aus den eingehenden Jsons verarbeitet werden
 */

@ApplicationScoped
public class SensorGenerator {
    private Random random = new Random();

    @Outgoing("generated-sensor")
    public Flowable<String> generate() {
        return Flowable.interval(10, TimeUnit.SECONDS)
                .map(tick -> "{\"id\":\"randomID"+random.nextInt(10000)+"\",\"detectionType\":\"drone\",\"channel\":\"alert\",\"protocol\":\"Wi-Fi\",\"version\":3,\"coordinates\":{\"longitude\":12.17302,\"altitude\":47.5839863,\"heading\":290.0}}");
    }

}
