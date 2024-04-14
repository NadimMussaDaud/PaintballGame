package dataStructures;

/**
 * This class provides an implementation of the Array interface with a dynamically resizing array.
 * @param <E> the type of elements stored in the array
 */
public class ArrayClass<E> implements Array<E> {
	/**
	 * Constants for resizing and searching
	 */
	private static final int FACTOR = 2;
	private static final int NOT_FOUND = -1;
	private static final int MAX_ELEMS = 50;

	/**
	 * Array to store elements
	 */
	private E[] elems;
	
	/**
	 * Counter that maintains the number of elements in the array
	 */
	private int counter;

    /**
     * Constructs an empty array with a default maximum capacity.
     */
	@SuppressWarnings("unchecked")
	public ArrayClass() {
		elems = (E[]) new Object[MAX_ELEMS];
		counter = 0;
	}

    /**
     * Constructs an empty array with the specified initial capacity.
     * @param dimension the initial capacity of the array
     */
	@SuppressWarnings("unchecked")
	public ArrayClass(int dimention) {
		elems = (E[]) new Object[dimention];
		counter = 0;
	}

	@Override
	public void insertLast(E e) {
		if (counter == elems.length) resize();
		elems[counter++] = e;
	}
	
	@Override
	public void insertAt(E e, int pos) {
		if (counter == elems.length) resize();
		for(int i = counter-1; i >= pos; i--)
			elems[i+1] = elems[i];
		elems[pos] = e;
		counter++;
	}

	@Override
	public void removeLast() {
		elems[--counter] = null;
	}

	@Override
	public void removeAt(int pos) {
		for(int i = pos; i< counter-1; i++)
			elems[i] = elems[i+1];
		elems[--counter] = null;
	}

	@Override
	public boolean searchForward(E e) {
		return searchIndexOf(e) != NOT_FOUND;
	}
	
	@Override
	public boolean searchBackward(E e) {
		int i = counter-1;
		boolean found = false;
		while (i>=0 && !found)
			if (elems[i].equals(e))
				found = true;
			else
				i--;
		return found;
	}

	@Override
	public int searchIndexOf(E e) {
		int i = 0;
		int result = NOT_FOUND;
		boolean found = false;
		while (i<counter && !found)
			if (elems[i].equals(e))
				found = true;
			else
				i++;
		if (found) result = i;
		return result;
	}

	@Override
	public E get(int pos) {
		return elems[pos];

	}

	@Override
	public int size() {
		return counter;

	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayIteratorClass<E>(elems, counter);
	}

    /**
     * Resizes the array to more elements when the current capacity is reached.
     * This method extends the size of the array when resizing.
     */
	@SuppressWarnings("unchecked")
	private void resize() {
		E tmp[] = (E[]) new Object[FACTOR*elems.length];
		for (int i=0;i<counter; i++)
			tmp[i] = elems[i];
		elems = tmp;
	}
}
