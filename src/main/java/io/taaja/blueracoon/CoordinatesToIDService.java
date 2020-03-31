package io.taaja.blueracoon;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;
import io.taaja.models.generic.Coordinates;
import io.taaja.models.zoning.LocationInformation;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.NotAllowedException;


@ApplicationScoped
@JBossLog
public class CoordinatesToIDService {

    @ConfigProperty(name = "purple-tiger.url")
    private String purpleTiger;

    private ObjectMapper objectMapper = new ObjectMapper();

    private HttpClient client;

    void onStart(@Observes StartupEvent ev) {
        this.client = HttpClientBuilder.create().build();
    }

    @SneakyThrows
    public String encode(Coordinates coordinates){

        HttpGet httpGet = new HttpGet(
                this.purpleTiger +
                        "?longitude=" + coordinates.getLongitude() +
                        "&latitude=" + coordinates.getLatitude() +
                        "&altitude=" + coordinates.getAltitude()
        );

        HttpResponse response = this.client.execute(httpGet);
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new NotAllowedException("cant resolve coordinates");
        }

        LocationInformation locationInformation = objectMapper.readValue(
                response.getEntity().getContent(),
                LocationInformation.class
        );

        return locationInformation.getExtensions().get(0).getUuid();
    }

}
