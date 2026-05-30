/**
 * Custom exception thrown when adding an exercise would exceed
 * the maximum allowed duration of a training session.
 */
public class TimeExceededException extends RuntimeException {
    /**
     * Constructs a new TimeExceededException with the specified detail message.
     * @param message The detail message.
     */
    public TimeExceededException(String message) {
        super(message);
    }
}
