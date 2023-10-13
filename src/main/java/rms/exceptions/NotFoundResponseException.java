package rms.exceptions;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class NotFoundResponseException extends NotFoundException {
    public NotFoundResponseException(String message) {
        super(Response
                .status(Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity(message)
                .build());
    }

    public NotFoundResponseException(Object body, MediaType type) {
        super(Response
                .status(Status.NOT_FOUND)
                .type(type)
                .entity(body)
                .build());
    }
}
