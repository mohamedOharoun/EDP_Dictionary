class Entry {
    private final Object key;
    private Object value;
    private final int hash;

    public Entry(Object key, Object value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    public Entry(Object key, Object value, int hash) {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }

    public Object getValue() {
        return this.value;
    }

    public Object getKey() {
        return this.key;
    }

    public int getHash() {
        return this.hash;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", key, value);
    }
}