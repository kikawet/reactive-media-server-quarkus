package rms.exceptions;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class BadRequestResponseException extends BadRequestException {
    public BadRequestResponseException(String message) {
        super(Response
                .status(Status.BAD_REQUEST)
                .type(MediaType.TEXT_PLAIN)
                .entity(message)
                .build());
    }

    public BadRequestResponseException(Object body, MediaType type) {
        super(Response
                .status(Status.BAD_REQUEST)
                .type(type)
                .entity(body)
                .build());
    }
}
