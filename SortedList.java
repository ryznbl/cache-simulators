import java.util.Iterator;

/**
 * A sorted linked list implementation of {@code BasicList} interface.
 * Elements in this list are ordered based on their natural ordering.
 * Defined by {@code Comparable} interface.
 *
 * @param <T> the type of elements stored in the sorted list,
 *           which must implement {@code Comparable} interface
 */
public class SortedList<T extends Comparable<T>> extends BasicList<T> { 

	/**
	 * Adds a new element to the sorted linked list in ascending order.
	 * @param value the value to be added to the sorted linked list.
	 * @throws IllegalArgumentException if the specified value is null
	 */
	public void add(T value){
		if (value == null) {
			throw new IllegalArgumentException("Null value is not allowed.");
		}

		Node<T> newNode = new Node<>(value);
		if (head == null) {
			addFirst(value);
		} else {
			Node<T> current = head;
			Node<T> prev = null;

			while (current != null && current.getData().compareTo(value) <= 0) {
				prev = current;
				current = current.getNext();
			}

			if (prev == null) {
				addFirst(value);
			} else if (current == null) {
				addLast(value);
			} else {
				size++;
				newNode.setNext(prev.getNext());
				prev.setNext(newNode);
			}
		}
	}
	
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//*******        Remember to add JavaDoc         *******
	//******************************************************

	/**
	 * Main method I used to test this class.
	 * @param args args used from command line.
	 */
	public static void main(String[] args) {
		SortedList<Integer> sortedList = new SortedList<>();

		// Add elements to the sorted list
		sortedList.add(5);
		sortedList.add(2);
		sortedList.add(8);
		sortedList.add(5);

		// Print the sorted list
		System.out.println("Sorted list: " + sortedList.listToString()); // Should print: 2 3 5 8

		// Test additional methods
		System.out.println("First element: " + sortedList.getFirst()); // Should print: 2
		System.out.println("Last element: " + sortedList.getLast()); // Should print: 8
		System.out.println("Index of 5: " + sortedList.indexOf(5)); // Should print: 2
		System.out.println("Index of 10: " + sortedList.indexOf(10)); // Should print: -1
	}	
}
