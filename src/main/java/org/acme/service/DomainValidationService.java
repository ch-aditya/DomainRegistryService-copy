package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.Errors;
import org.acme.utils.DomainValidator;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    public boolean validateDomain(List<String> domains) {
        if(Objects.isNull(domains) || domains.isEmpty()) {
            log.warn("No domain provided in the request.");
            return false;
        }
        domains.forEach(domain -> {
            if(!DomainValidator.isValidDomain(domain) && !isDNSValid(domain)) {
                log.warn("Invalid domain provided, Skipping Domain Name: {}", domain);
                return;
            }
            log.info("Valid domain, Domain Name: {}", domain);
        });
        return true;
    }

    /**
     * Function used to lookup domain
     * @param domain "Domain Name"
     * @return boolean (True if valid domain,
     * false if invalid domain)
     */
    private boolean isDNSValid(String domain) {
        log.info("Checking DNS validity for domain: {}", domain);
        try {
            InetAddress address = InetAddress.getByName(domain);
            log.info("Domain resolved successfully: {}, Address: {}", domain, address);
            return address != null;
        } catch (UnknownHostException ex) {
            log.warn("Domain could not be resolved: {}", domain);
            return false;
        }
    }
}
