package exceptions;

public class EmptyTaskListException extends RuntimeException {
    public EmptyTaskListException() {}
    public EmptyTaskListException(String message) {
        super(message);
    }
}
