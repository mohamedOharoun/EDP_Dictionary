class ValuesList {
    private Entry[] values;
    private int index = 0;
    private int n_entries = 0;

    public ValuesList(Entry[] values) {
        this.values = values;
    }

    Entry get(int i) {
        return values[i];
    }

    void delete(int i) {
        values[i] = null;
        n_entries--;
    }

    void add(Entry obj) {
        values[index] = obj;
        index++;
        n_entries++;
    }

    void setArray(Entry[] entries) {
        values = entries;
        index = 0;
        n_entries = 0;
    }

    void replace(int i, Entry obj) {
        values[i] = obj;
    }

    int capacity() {
        return values.length;
    }

    int length() {
        return index;
    }

    int size() {
        return n_entries;
    }

    Entry[] getAll() {
        return values;
    }

    boolean containsValue(Object v) {
        for(int i = 0; i < length(); i++) {
            if(get(i) == null) continue;
            if(v.equals(get(i).getValue())) return true;
        }
        return false;
    }
}
