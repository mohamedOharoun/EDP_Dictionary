class IndexesList {
    private Integer[] indexes;

    public IndexesList(Integer[] indexes) {
        this.indexes = indexes;
    }

    void put(int i, int value) {
        indexes[i] = value;
    }

    Integer get(Integer i) {
        return indexes[i];
    }

    void setArray(Integer[] indexes) {
        this.indexes = indexes;
    }

    int length() {
        return indexes.length;
    }
}
