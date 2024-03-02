public class ValuesList {
    private Entry[] values;
    private int index = 0;
    private int n_entries = 0;

    public ValuesList(Entry[] values) {
        this.values = values;
    }

    public Entry get(int i) {
        return values[i];
    }

    public void delete(int i) {
        values[i] = null;
        n_entries--;
    }

    public void add(Entry obj) {
        values[index] = obj;
        index++;
        n_entries++;
    }

    public void setArray(Entry[] entries) {
        values = entries;
        index = 0;
        n_entries = 0;
    }

    public void add(int i, Entry obj) {
        values[i] = obj;
        n_entries++;
    }

    public int capacity() {
        return values.length;
    }

    public int length() {
        return index;
    }

    public int size() {
        return n_entries;
    }

    public Entry[] getAll() {
        return values;
    }
}
