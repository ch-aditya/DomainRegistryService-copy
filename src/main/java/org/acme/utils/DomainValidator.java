package org.acme.utils;

public class DomainValidator {

    private DomainValidator() {}

    private static final String DOMAIN_REGEX = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.[A-Za-z]{2,})+$\n";

    /**
     * Utility function used ot validate
     * domain name format.
     * @param domain "Domain Name"
     * @return boolean (True if it's a valid domain,
     * false if it's invalid)
     */
    public static boolean isValidDomain(String domain) {
        return domain != null && domain.trim().matches(DOMAIN_REGEX);
    }

}
