package dataStructures;

/**
 * This interface represents an iterator over a collection of elements of type E.
 * @param <E> the type of elements in the collection
 */
public interface Iterator<E> {

    /**
     * Checks if there are more elements in the collection to iterate over.
     * @return <code>true</code> if there are more elements, <code>false</code> otherwise
     */
    boolean hasNext();

    /**
     * Returns the next element in the collection.
     * @return the next element in the collection
     * @pre hasNext()
     */
    E next();
}