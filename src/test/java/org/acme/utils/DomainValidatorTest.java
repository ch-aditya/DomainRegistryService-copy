package org.acme.utils;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class DomainValidatorTest {


    @ParameterizedTest
    @ValueSource(strings = {
            "example.com",
            "sub.example.com",
            "example-domain.com",
            "example.co.uk",
            "example123.com",
            "a.com",
    })
    void givenValidDomain_whenIsValidDomainFormatCalled_thenReturnTrue(String domain) {
        assertTrue(DomainValidator.isValidDomainFormat(domain));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "example.com",
            "example-domain.com",
            "example.co.uk",
            "example123.com",
            "google.com",
            "dataarize.com"
    })
    void givenValidDomain_whenIsDomainValidCalled_thenReturnTrue(String domain) {
        assertTrue(DomainValidator.isDomainValid(domain));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "-example.com",
            "example-.com",
            "example..com",
            "example",
            "example.c",
            "this.is.a.very.long.subdomain.name.exceeding.sixty.three.characters.com", // Long subdomain
    })
    void givenInvalidDomain_whenIsValidDomainFormatCalled_thenReturnFalse(String domain) {
        assertFalse(DomainValidator.isValidDomainFormat(domain));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-example.com",
            "example-.com",
            "example..com",
            "example",
            "example.c",
    })
    void givenInvalidDomain_whenIsDomainValidCalled_thenReturnFalse(String domain) {
        assertFalse(DomainValidator.isDomainValid(domain));
    }

}