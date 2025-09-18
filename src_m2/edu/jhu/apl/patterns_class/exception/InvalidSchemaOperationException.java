package edu.jhu.apl.patterns_class.exception;

public class InvalidSchemaOperationException extends RuntimeException {

    public InvalidSchemaOperationException() {
        super();
    }

    public InvalidSchemaOperationException(String message) {
        super(message);
    }

    public InvalidSchemaOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSchemaOperationException(Throwable cause) {
        super(cause);
    }
}