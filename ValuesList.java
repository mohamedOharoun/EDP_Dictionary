public class ValuesList {
    private Entry[] values;
    private int index = 0;

    public ValuesList(Entry[] values) {
        this.values = values;
    }

    public Entry get(int i) {
        return values[i];
    }

    public void delete(int i) {
        values[i] = null;
    }

    public void add(Entry obj) {
        values[index] = obj;
        index++;
    }

    public void add(int i, Entry obj) {
        values[i] = obj;
    }

    public int len() {
        return values.length;
    }

    public Entry[] getAll() {
        return values;
    }
}
