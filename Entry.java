class Entry {
    private final int hash;
    private Pair keyValue;

    public Entry(Object key, Object value) {
        this.keyValue = new Pair(key, value);
        this.hash = key.hashCode();
    }

    public Entry(Object key, Object value, int hash) {
        this.keyValue = new Pair(key, value);
        this.hash = hash;
    }

    public Entry(Pair p) {
        this.keyValue = new Pair(p.get(0), p.get(1));
        this.hash = p.get(0).hashCode();
    }

    public Object getValue() {
        return this.keyValue.get(1);
    }

    public Object getKey() {
        return this.keyValue.get(0);
    }

    public int getHash() {
        return this.hash;
    }

    public Pair getItem() {
        return keyValue;
    }

    public void setValue(Object value) {
        this.keyValue = new Pair(getKey(), value);
    }
}