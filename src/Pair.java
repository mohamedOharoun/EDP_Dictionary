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
        return String.format("(%s, %s)", pairs[0], pairs[1]);
    }
}
