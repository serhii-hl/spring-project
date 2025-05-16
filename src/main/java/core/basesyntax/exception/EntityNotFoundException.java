package core.basesyntax.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Exception e) {
        super(message);
    }
}
