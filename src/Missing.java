/**
 * The Missing interface provides a workaround for implementing the __missing__ magic method
 * in Python for a custom dictionary.
 */
public interface Missing {
    /**
     * This method is invoked when a key is not found in the dictionary when the retrieve(equivalent to accesing a key using brackets) method is used.
     *
     * @param key The key that was not found in the dictionary.
     * @return The value to be returned when the key is missing.
     */
    public Object missing(Object key);
}
