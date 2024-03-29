/**
 * The ValueError class represents an exception that is thrown when a dictionary update sequence element does not meet the required length criteria, which is 2.
 * This exception is similar to the ValueError in Python, indicating that a dictionary update sequence element has an incorrect length, which should be 2.
 */
public class ValueError extends RuntimeException {

    /**
     * Constructs a new ValueError with the specified sequence element index and its length.
     *
     * @param sequenceElement The index of the dictionary update sequence element.
     * @param lengthSequence  The length of the dictionary update sequence element.
     */
    public ValueError(int sequenceElement, int lengthSequence) {
        super(String.format("dictionary update sequence element #%d has length %d; 2 is required", sequenceElement, lengthSequence));
    }
}