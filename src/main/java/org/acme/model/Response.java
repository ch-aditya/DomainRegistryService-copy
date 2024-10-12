package org.acme.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Response {
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    public Response(String message) {
        this.message = message;
    }
}
