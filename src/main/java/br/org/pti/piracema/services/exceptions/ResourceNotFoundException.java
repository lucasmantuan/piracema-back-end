package br.org.pti.piracema.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object value) {
        super("Resource does not exist: Value = " + value);
    }

    public ResourceNotFoundException() {
        super("Resource does not exist");
    }
}
