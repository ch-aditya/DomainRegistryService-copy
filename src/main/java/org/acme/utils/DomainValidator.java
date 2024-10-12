package org.acme.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class DomainValidator {

    private DomainValidator() {}

    private static final String DOMAIN_REGEX = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.[A-Za-z]{2,})+$";

    /**
     * Utility function used ot validate
     * domain name format.
     * @param domain "Domain Name"
     * @return boolean (True if it's a valid domain,
     * false if it's invalid)
     */
    public static boolean isValidDomainFormat(String domain) {
        return domain != null && domain.trim().matches(DOMAIN_REGEX);
    }

    /**
     * Function used to lookup domain
     * @param domain "Domain Name"
     * @return boolean (True if valid domain,
     * false if invalid domain)
     */
    public static boolean isDomainValid(String domain) {
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
