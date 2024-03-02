public class IndexesList {
    private Integer[] indexes;

    public IndexesList(Integer[] indexes) {
        this.indexes = indexes;
    }

    public void put(int i, int value) {
        indexes[i] = value;
    }

    public Integer get(Integer i) {
        return indexes[i];
    }

    public void setArray(Integer[] indexes) {
        this.indexes = indexes;
    }

    public int length() {
        return indexes.length;
    }
}
