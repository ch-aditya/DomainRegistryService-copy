package org.acme.utils;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.Errors;
import org.acme.exceptions.DNSAlreadyExistsException;
import org.acme.exceptions.InvalidDomainNameException;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import java.util.Arrays;

@Slf4j
public class DNSValidator {

    private DNSValidator() {}

    /**
     * Looks up for NS record entries
     * @param domain
     * @return
     */
    public static Uni<Object> doesNameServerExists(String domain) {
        return Uni.createFrom().item(() -> {
            try {
                log.info("Checking for NS records for domain: {}", domain);
                Lookup lookup = new Lookup(domain, Type.NS);
                Record[] records = lookup.run();
                if (lookup.getResult() == Lookup.SUCCESSFUL) {
                    Arrays.asList(records).forEach(rec -> log.info("NS record: {}", rec));
                    throw new DNSAlreadyExistsException(Errors.NS_RECORD_ALREADY_EXISTS + domain);
                }
                log.info("No NS record found for domain: {}", domain);
                return null;
            } catch (TextParseException ex) {
                log.error("Invalid domain name: {}", domain, ex);
                throw new InvalidDomainNameException(Errors.INVALID_DOMAIN_PROVIDED + domain);
            }
        }).runSubscriptionOn(Infrastructure.getDefaultExecutor());
    }
}
