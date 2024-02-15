import java.util.Arrays;

public class Dictionary {
    
    private static final int CAPACITY_INITIAL = 10;

    private Object[] keys;
    private Object[] values;
    private int size;

    public Dictionary() {

        keys = new Object[CAPACITY_INITIAL];
        values = new Object[CAPACITY_INITIAL];
        size = 0;

    }

    public void addElement(Object key, Object value) {

        if (size == (int) keys.length * 2/3) {
            expandArrays();
        }

        keys[size * 4] = key;
        values[size] = value;
        size++;

    }

    public Object getValue(Object key) throws Exception {

        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }

        throw new Exception("Clave no encontrada en el diccionario.");

    }

    private void expandArrays() {
        int newCapacity;
        if(keys.length < 5000) {
            newCapacity = keys.length * 4;
        } else {
            newCapacity = keys.length * 2;
        }

        keys = Arrays.copyOf(keys, newCapacity);
        values = Arrays.copyOf(values, newCapacity);

    }

}