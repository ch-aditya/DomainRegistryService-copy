package org.acme.utils;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.exceptions.DNSAlreadyExistsException;
import org.acme.exceptions.InvalidDomainNameException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class DNSValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "google.com",
            "sub.example.com",
            "example-domain.com",
            "example.co.uk",
            "example123.com",
            "a.com",
    })
    void givenValidDomainWithDNS_whenDoesNameServerExistsCalled_thenThrowException(String domain) {
        assertThrows(DNSAlreadyExistsException.class, () -> DNSValidator.doesNameServerExists(domain).await().indefinitely());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ""
    })
    void givenValidDomainWithoutDNS_whenDoesNameServerExistsCalled_thenThrowException(String domain) {
        assertThrows(InvalidDomainNameException.class, () -> DNSValidator.doesNameServerExists(domain).await().indefinitely());

    }

    @ParameterizedTest
    @ValueSource(strings = {
            " "
    })
    void givenValidDomainWithoutDNS_whenDoesNameServerExistsCalled_thenReturnNothing(String domain) {
        assertNull(DNSValidator.doesNameServerExists(domain).await().indefinitely());

    }

}