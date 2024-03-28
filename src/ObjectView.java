import java.util.Iterator;

public interface ObjectView<T> {
    public int length();
    public boolean contains(T other);
    public Iterator<T> iterator();
    public Iterable<T> reversed();
}
