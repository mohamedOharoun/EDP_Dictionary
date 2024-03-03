class Entry extends Pair {
    private final int hash;

    public Entry(Object key, Object value) {
        super(key, value);
        this.hash = key.hashCode();
    }

    public Entry(Object key, Object value, int hash) {
        super(key, value);
        this.hash = hash;
    }

    public Entry(Pair p) {
        super(p.get(0), p.get(1));
        this.hash = p.get(0).hashCode();
    }

    public Object getValue() {
        return this.get(1);
    }

    public Object getKey() {
        return this.get(0);
    }

    public int getHash() {
        return this.hash;
    }

    public void setValue(Object value) {
        this.pairs[1] = value;
    }
}