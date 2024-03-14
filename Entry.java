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
        this.keyValue = p;
        this.hash = p.get(0).hashCode();
    }

    public Entry(Pair p, int hash) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if((keyValue.get(0) instanceof String || keyValue.get(0) instanceof Character)) {
            sb.append("\'"); sb.append(keyValue.get(0)); sb.append("\'");
        } else {sb.append(keyValue.get(0));}
        sb.append(", ");
        if((keyValue.get(1) instanceof String || keyValue.get(1) instanceof Character)) {
            sb.append("\'"); sb.append(keyValue.get(1)); sb.append("\'");
        } else {sb.append(keyValue.get(1));}
        sb.append(")");  

        return sb.toString();
    }
}