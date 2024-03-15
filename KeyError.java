public class KeyError extends RuntimeException {
    private Object key;

    public KeyError(Object key) {
        super(((key instanceof String || key instanceof Character) ? "'" + key + "'" : String.valueOf(key)));
        this.key = key.toString();
    }

    public Object getKey() {
        return key;
    }

}
