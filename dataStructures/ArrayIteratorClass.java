package dataStructures;

/**
 * This class implements an iterator an over an array
 * @param <E> the type of elements in the array
 */
public class ArrayIteratorClass<E> implements Iterator<E> {

    /**
     * Array of elements to iterate over
     */
    private E[] elems;
    
    /**
     * Number of elements in the array
     */
    private int counter;
    
    /**
     * Index of the current element
     */
    private int current;

    /**
     * Constructs an ArrayIterator with the given array of elements and the counter.
     * @param elems   the array of elements to iterate over
     * @param counter the number of elements in the array
     */
    public ArrayIteratorClass(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < counter;
    }

    @Override
    public E next() {
        return elems[current++];
    }
}
