package theschoolmanagmentsystem.domain.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
}
