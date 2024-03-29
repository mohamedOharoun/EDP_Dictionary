import java.util.Iterator;

/**
 * The ObjectView interface represents an object view in Python. It provides methods for interacting
 * with the view of objects stored in a data structure, allowing for length retrieval, containment checks,
 * iteration, and reverse iteration.
 *
 * @param <T> The type of objects stored in the view.
 */
public interface ObjectView<T> {

    /**
     * Returns the length of the object view, which corresponds to the number of elements in the view.
     *
     * @return The length of the object view.
     */
    public int length();

    /**
     * Checks whether the specified element is contained in the object view.
     *
     * @param other The element to check for containment.
     * @return True if the element is contained in the object view, false otherwise.
     */
    public boolean contains(T other);

    /**
     * Returns an iterator over the elements of the object view. Iteration should be performed in the
     * same order as the elements are stored in the object that is viewed.
     *
     * @return An iterator over the elements of the object view.
     */
    public Iterator<T> iterator();

    /**
     * Returns an iterable object that allows reverse iteration over the elements of the object view.
     * Reverse iteration should be performed in the opposite order as the elements are stored in the object that is viewed.
     *
     * @return An iterable object for reverse iteration over the elements of the object view.
     */
    public Iterable<T> reversed();
}
