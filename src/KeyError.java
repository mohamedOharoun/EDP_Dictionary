/**
 * The KeyError class represents an exception that is raised when a key is not found in a dictionary.
 * It is analogous to the KeyError exception in Python, which occurs when attempting to access a non-existent key
 * in a dictionary.
 */
public class KeyError extends RuntimeException {

    /**
     * Constructs a new KeyError with the specified key.
     *
     * @param key The key that caused the KeyError. It can be of any object type.
     */
    public KeyError(Object key) {
        super(((key instanceof String || key instanceof Character) ? "'" + key + "'" : String.valueOf(key)));
    }
}