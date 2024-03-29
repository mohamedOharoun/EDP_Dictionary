/**
 * The TypeError class represents an exception that is raised when an object is of an unhashable type.
 * In Python, this error typically occurs when trying to use an unhashable type as a dictionary key.
 */
public class TypeError extends RuntimeException {

    /**
     * Constructs a new TypeError with the specified key.
     *
     * @param key The key that caused the TypeError. It can be of any object type.
     */
    public TypeError(Object key) {
        super(String.format("unhashable type: '%s'", key == null ? "null" : key.getClass()));
    }
}