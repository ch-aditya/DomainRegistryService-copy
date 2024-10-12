package org.acme.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.Errors;
import org.acme.exceptions.DNSAlreadyExistsException;
import org.acme.exceptions.InputFormatException;
import org.acme.exceptions.InvalidDomainNameException;
import org.acme.utils.DNSValidator;
import org.acme.utils.DomainValidator;

import java.util.List;
import java.util.Objects;

@Slf4j
@ApplicationScoped
public class DomainValidationService {

    /**
     * Function used to validate the following:
     * 1. Validate the structure of the domain
     * 2. Verify if it's a valid domain
     * @param domains "List of domains"
     */
    public Uni<Void> validateDomain(List<String> domains) {
        if (Objects.isNull(domains) || domains.isEmpty()) {
            log.warn("No domain provided in the request.");
            return Uni.createFrom().failure(new InputFormatException(Errors.NO_DOMAINS_PROVIDED));
        }

        return Multi.createFrom().items(domains.stream())
                .onItem().transformToUniAndConcatenate(domain -> {
                    // Check each domain format reactively
                    if (!DomainValidator.isValidDomainFormat(domain) || !DomainValidator.isDomainValid(domain)) {
                        log.warn("Invalid domain: {}", domain);
                        return Uni.createFrom().failure(new InvalidDomainNameException(Errors.INVALID_DOMAIN_PROVIDED + domain));
                    }
                    return Uni.createFrom().voidItem();
                })
                .collect().asList()
                .replaceWithVoid();
    }

    /**
     * Function used to validate if the domain
     * already has an DNS entry.
     * @param domains "List of domains"
     * @return void
     */
    public Uni<Void> validateDNSEntries(List<String> domains) {
        return Uni.combine().all().unis(domains.stream()
                .map(domain -> DNSValidator.doesNameServerExists(domain)
                        .onItem().transformToUni(result -> {
                            if (Boolean.TRUE.equals(result)) {
                                log.warn("NS records already exist for domain: {}", domain);
                                return Uni.createFrom().failure(new DNSAlreadyExistsException(Errors.NS_RECORD_ALREADY_EXISTS + domain));
                            }
                            log.info("No NS records found for domain: {}. Proceeding to create DNS entry...", domain);
                            return createDNSEntries();
                        })
                ).toList()
        ).discardItems();
    }


    public Uni<Void> createDNSEntries() {
        return Uni.createFrom().nullItem();
    }


}
