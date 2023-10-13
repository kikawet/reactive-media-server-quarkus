package rms.exceptions;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ForbiddenResponseException extends ForbiddenException {
    public ForbiddenResponseException(String message) {
        super(Response
                .status(Status.FORBIDDEN)
                .type(MediaType.TEXT_PLAIN)
                .entity(message)
                .build());
    }

    public ForbiddenResponseException(Object body, MediaType type) {
        super(Response
                .status(Status.FORBIDDEN)
                .type(type)
                .entity(body)
                .build());
    }
}
