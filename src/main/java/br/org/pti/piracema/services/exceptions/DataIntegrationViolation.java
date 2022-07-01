package br.org.pti.piracema.services.exceptions;

public class DataIntegrationViolation extends RuntimeException {

    public DataIntegrationViolation(String msg) {
        super(msg);
    }

}
