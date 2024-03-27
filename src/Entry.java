class Entry {
    private final int hash;
    private Pair keyValue;

    Entry(Object key, Object value) {
        this.keyValue = new Pair(key, value);
        this.hash = key.hashCode();
    }

    Entry(Object key, Object value, int hash) {
        this.keyValue = new Pair(key, value);
        this.hash = hash;
    }

    Entry(Pair p) {
        this.keyValue = p;
        this.hash = p.get(0).hashCode();
    }

    Entry(Pair p, int hash) {
        this.keyValue = p;
        this.hash = hash;
    }

    Object getValue() {
        return this.keyValue.get(1);
    }

    Object getKey() {
        return this.keyValue.get(0);
    }

    int getHash() {
        return this.hash;
    }

    Pair getItem() {
        return keyValue;
    }

    void setValue(Object value) {
        this.keyValue = new Pair(getKey(), value);
    }

    @Override
    public String toString() {
        return keyValue.toString();
    }
}