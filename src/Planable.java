/**
 * Interface representing any item that can be planned within a training session.
 * Defines the basic contract for elements that require time management.
 */
public interface Planable {
    /**
     * Gets the duration of the planable item.
     * @return Duration in minutes.
     */
    int getDurationInMinutes();
    /**
     * Gets the name of the planable item.
     * @return The name as a String.
     */
    String getName();
}
