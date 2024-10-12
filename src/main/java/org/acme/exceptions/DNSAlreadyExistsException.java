package org.acme.exceptions;

public class DNSAlreadyExistsException extends RuntimeException{
    public DNSAlreadyExistsException(String message) {
        super(message);
    }
}
