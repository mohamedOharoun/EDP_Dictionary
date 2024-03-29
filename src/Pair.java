import java.util.Arrays;

/**
 * Represents a pair of two elements, similar to a tuple in Python the elements cannot be changed.
 */
public class Pair {
    private final Object[] pairs = new Object[2];

    /**
     * Constructs a pair with two given objects.
     *
     * @param obj1 The first object of the pair.
     * @param obj2 The second object of the pair.
     */
    public Pair(Object obj1, Object obj2) {
        this.pairs[0] = obj1;
        this.pairs[1] = obj2;
    }
        
    /**
     * Retrieves the element at the specified index in the pair.
     *
     * @param i The index of the element to retrieve.
     * @return The element at the specified index.
     */
    public Object get(int i) {
        return pairs[i];
    }
    
    /**
     * Converts the pair object to a string representation, if any element is a String or a Character/char will be sorrounded with simple quotes.
     *
     * @return A string representation of the pair, formatted as (obj1, obj2).
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

    /**
     * Computes the hash code for the pair.
     *
     * @return The hash code value for the pair.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(pairs);
    }

    /**
     * Indicates whether some other object is "equal to" this one. Two Pairs are said to be equal if they have the same elements in the same order.
     *
     * @param other The reference object with which to compare.
     * @return True if this object is the same as the other object; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Pair)) return false;
        Pair o = (Pair) other;
        return pairs[0].equals(o.get(0)) && pairs[1].equals(o.get(1));
    }
}
