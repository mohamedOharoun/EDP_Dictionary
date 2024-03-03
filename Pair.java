public class Pair {
    protected final Object[] pairs = new Object[2];
    
    public Pair(Object obj1, Object obj2) {
        this.pairs[0] = obj1;
        this.pairs[1] = obj2;
    }
        
    public Object get(int i) {
        return pairs[i];
    }
    
    @Override
    public String toString() {
        return String.format("(%s, %s)", pairs[0], pairs[1]);
    }
}
