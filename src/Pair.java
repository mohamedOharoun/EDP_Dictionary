import java.util.Arrays;

public class Pair {
    protected final Object[] pairs = new Object[2];
    

    public Pair(Object obj1, Object obj2) {
        this.pairs[0] = obj1;
        this.pairs[1] = obj2;
    }
        
    /** Este método accede a un elemento del array Pair[].
     * 
     * @param i El número del elemento al que se quiera acceder.
     * @return El elemento (objeto tipo Pair).
     */
    public Object get(int i) {
        return pairs[i];
    }
    
    /** Este método covierte a String el objeto Pair.
     * 
     * @return Una string formada por los elementos de Pair.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if((get(0) instanceof String || get(0) instanceof Character)) {
            sb.append("\'"); sb.append(get(0)); sb.append("\'");
        } else {sb.append(get(0));}
        sb.append(", ");
        if((get(1) instanceof String || get(1) instanceof Character)) {
            sb.append("\'"); sb.append(get(1)); sb.append("\'");
        } else {sb.append(get(1));}
        sb.append(")");  

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(pairs);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Pair)) return false;
        Pair o = (Pair) other;
        return pairs[0].equals(o.get(0)) && pairs[1].equals(o.get(1));
    }
}
