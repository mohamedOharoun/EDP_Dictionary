public class KeyError extends RuntimeException {
    private String key;

    public KeyError(String key) {
        super("KeyError: '" + key + "'");
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
