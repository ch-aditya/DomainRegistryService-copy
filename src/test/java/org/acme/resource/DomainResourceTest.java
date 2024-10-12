package org.acme.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DomainResourceTest {

    @Test
    void givenValidDomain_whenDomainsValidateCalled_thenReturn200() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonList("google.com"))
                .when().post("/domains/validate")
                .then()
                .statusCode(200);
    }

    @Test
    void givenValidDomain_whenDomainsValidateCalled_thenReturn409() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonList("-google.com"))
                .when().post("/domains/validate")
                .then()
                .statusCode(409);
    }

    @Test
    void givenValidDomainWithDNS_whenDomainsLookupCalled_thenReturn409() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonList("google.com"))
                .when().post("/domains/lookup")
                .then()
                .statusCode(409);
    }

    @Test
    void givenValidDomainWithDNS_whenDomainsLookupCalled_thenReturn200() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonList(" "))
                .when().post("/domains/lookup")
                .then()
                .statusCode(200);
    }
}