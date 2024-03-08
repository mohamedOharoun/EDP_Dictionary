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