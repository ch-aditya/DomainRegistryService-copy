package org.acme.exceptions;

public class InvalidDomainNameException extends RuntimeException{
    public InvalidDomainNameException(String message) {
        super(message);
    }
}
