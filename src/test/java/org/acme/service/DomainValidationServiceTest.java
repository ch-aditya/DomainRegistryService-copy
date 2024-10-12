package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.exceptions.DNSAlreadyExistsException;
import org.acme.exceptions.InputFormatException;
import org.acme.exceptions.InvalidDomainNameException;
import org.acme.utils.DNSValidator;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DomainValidationServiceTest {

    @Inject
    private DomainValidationService domainValidationService;

    @Test
    void givenEmptyList_whenValidateDomainCalled_thenThrowException() {
        assertThrows(InputFormatException.class, () -> domainValidationService.validateDomain(Collections.emptyList()).await().indefinitely());
    }

    @Test
    void givenInvalidDomain_whenValidateDomainCalled_thenThrowException() {
        assertThrows(InvalidDomainNameException.class, () -> domainValidationService.validateDomain(Collections.singletonList("-example.com")).await().indefinitely());
    }

    @Test
    void givenValidDomain_whenValidateDomainCalled_thenReturnNull() {
        assertNull(domainValidationService.validateDomain(Collections.singletonList("google.com")).await().indefinitely());
    }

    @Test
    void givenValidDomainWithDNS_whenValidateDNSEntriesCalled_thenThrowException() {
        assertThrows(DNSAlreadyExistsException.class, () -> domainValidationService.validateDNSEntries(Collections.singletonList("google.com")).await().indefinitely());
    }

    @Test
    void givenValidDomainWithoutDNS_whenValidateDNSEntriesCalled_thenReturnNull() {
        assertNull(domainValidationService.validateDNSEntries(Collections.singletonList(" ")).await().indefinitely());
    }

}