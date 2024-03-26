public class TypeError extends RuntimeException {
    public TypeError(Object key) {
        super(String.format("unhashable type: '%s'", key.getClass()));
    }
}
