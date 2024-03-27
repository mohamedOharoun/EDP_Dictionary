class EntriesList {
    private Entry[] entries;
    private int index = 0;
    private int n_entries = 0;

    EntriesList(Entry[] values) {
        this.entries = values;
    }

    Entry get(int i) {
        return entries[i];
    }

    void delete(int i) {
        entries[i] = null;
        n_entries--;
    }

    void add(Entry obj) {
        entries[index] = obj;
        index++;
        n_entries++;
    }

    void setArray(Entry[] entries) {
        this.entries = entries;
        index = 0;
        n_entries = 0;
    }

    void replace(int i, Entry obj) {
        entries[i] = obj;
    }

    int capacity() {
        return entries.length;
    }

    int size() {
        return n_entries;
    }

    Entry[] getAll() {
        return entries;
    }

    boolean containsValue(Object v) {
        for(int i = 0; i < size(); i++) {
            if(get(i) == null) continue;
            if(v.equals(get(i).getValue())) return true;
        }
        return false;
    }
}
