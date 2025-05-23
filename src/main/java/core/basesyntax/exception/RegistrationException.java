package core.basesyntax.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message, RuntimeException e) {
        super(message);
    }
}
