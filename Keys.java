public class Keys {
    private IndexesList indexes;
    private ValuesList values;

    public Keys(IndexesList indexes, ValuesList values) {
        this.indexes = indexes;
        this.values = values;
    }

    public int length() {
        return values.size();
    }
}
