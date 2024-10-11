package org.acme.resource;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.constants.Errors;
import org.acme.constants.Success;
import org.acme.service.DomainValidationService;

import java.util.List;

@Slf4j
@Path("/domains")
public class DomainResource {


    private final DomainValidationService domainValidationService;

    @Inject
    public DomainResource(DomainValidationService domainValidationService) {
        this.domainValidationService = domainValidationService;
    }

    /**
     * version: 1.0.0
     * API is used to get list of domains from
     * the user and create DNS entries
     * @param domains "List of domains"
     * @return "Domains Received"
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> submitDomains(List<String> domains) {
        log.info("Received request to submit domains: {}", domains);
        return Uni.createFrom().item(() -> {
            if(!domainValidationService.validateDomain(domains)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(Errors.NO_DOMAINS_PROVIDED).build();
            }
            log.info("Successfully processed {} domains.", domains.size());
            return Response.status(Response.Status.CREATED).entity(Success.DOMAINS_RECEIVED_AND_PROCESSED).build();
        }).onFailure()
                .invoke(e -> log.error("Error processing domains: {}", domains, e));


    }
}
