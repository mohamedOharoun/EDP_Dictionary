public class Values {
    ValuesList values;

    public Values(ValuesList values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < values.size(); i++) {
            if(values.get(i) != null) {
                sb.append(values.get(i).getValue());
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
