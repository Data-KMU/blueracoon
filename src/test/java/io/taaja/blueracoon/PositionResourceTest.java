package io.taaja.blueracoon;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PositionResourceTest {

//    @Test
//    public void testHelloEndpoint() {
//        given()
//          .when().get("/dedrone/v1")
//          .then()
//             .statusCode(200)
//             .body(is("[{\"id\":\"5d77941ffa50637e98ca5845\",\"detectionType\":\"drone\",\"channel\":\"alert\",\"protocol\":\"Wi-Fi\",\"version\":0,\"coordinates\":{\"longitude\":12.17302,\"altitude\":47.5839863,\"heading\":290.0}},{\"id\":\"5d779403fa50637e98ca5844\",\"detectionType\":\"remote\",\"channel\":\"alert\",\"protocol\":\"LBT\",\"version\":0,\"coordinates\":{\"longitude\":12.17302,\"altitude\":47.5839863,\"heading\":290.0}}]"));
//    }

}
