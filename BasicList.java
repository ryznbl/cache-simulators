import java.util.Iterator;

/**
 * A basic singly linked list implementation without using arrays.
 * This list supports various operations including adding, removing, and moving elements.
 *
 * @param <T> the type of elements stored in the list
 */
public class BasicList<T> implements Iterable<T> { 

	//******************************************************
	//*******     BELOW THIS LINE IS PROVIDED code   *******
	//*******             Do NOT edit code!          *******
	//*******         Remember to add JavaDoc        *******
	//******************************************************

	/**
	 * Initialize a node we will point to the first position.
	 */
	protected Node<T> head = null;

	/**
	 * Returns an iterator over the elements in the list.
	 * @return an iterator over the elements in the list
	 */
	public Iterator<T> iterator() {
		//Return an iterator that traverses from
		//the beginning to the end of the list.
		//This provided code would work if you have set up the list correctly.
		
		return new Iterator<T>() {
			Node<T> current = head;
			
			public boolean hasNext(){
				return current!=null;
			}
				
			//next() would throw a NullPointerException
			//if you try to use next when there are no more items 
			public T next(){
				T toReturn = current.getData();
				current = current.getNext();
				return toReturn;
			}
		};
	}
	//******************************************************
	//*******    	END of PROVIDED Code 	 		 *******
	//*******    	Do NOT Change PROVIDED Code 	 *******
	//******************************************************

	/**
	 * Node we will point at the last position.
	 */
	private Node<T> tail;
	/**
	 * Variable that will keep track of the size.
	 */
	protected int size;

	/**
	 * Initializes an empty list.
	 */
	public BasicList(){
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Returns the number of items in the list.
	 * @return the number of items in the list
	 */
	public int size(){
		return size;
	}

	/**
	 * Returns the first value from the beginning of the list without removing it.
	 * @return the first value in the list, or null if the list is empty
	 */
	public T getFirst() {
		if (head == null) {
			return null;
		} else {
			return head.getData();
		}
	}

	/**
	 * Inserts a new node with the given value at the beginning of the list.
	 * @param value the value to add to the beginning of the list
	 * @throws IllegalArgumentException if the value is null
	 */
	public void addFirst(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Null value is not allowed.");
		}
		size++;
		Node<T> replacementNode = new Node<>(value);
		if(head == null) {
			tail = replacementNode;
        } else {
			replacementNode.setNext(head);
        }
        head = replacementNode;
    }

	/**
	 * Removes and returns the first value from the beginning of the list.
	 * @return the removed value, or null if the list is empty
	 */
	public T removeFirst(){
		if (head == null) {
			return null;
		}
		size--;
		T firstNode = head.getData();
		if (head.getNext() == null) {
			head = null;
			tail = null;
		} else {
			head = head.getNext();
		}
		return firstNode;
	}

	/**
	 * Returns the last value from the end of the list without removing it.
	 * @return the last value in the list, or null if the list is empty
	 */
	public T getLast() {
		if (tail == null) {
        	return null;
		}
		return tail.getData();
	}


	/**
	 * Appends a new value at the end of the list.
	 * @param value the value to add to the end of the list
	 * @throws IllegalArgumentException if the value is null
	 */
	public void addLast(T value) {		
		if (value == null) {
			throw new IllegalArgumentException("Null value is not allowed.");
		}
		size++;
		Node<T> addNode = new Node<>(value);
		if (head == null) {
			head = addNode;
        } else {
			tail.setNext(addNode);
        }
        tail = addNode;
    }


	/**
	 * Removes and returns the last value from the end of the list.
	 * @return the removed value, or null if the list is empty
	 */
	public T removeLast(){
		if (head == null) {
			return null;
		}
		size--;
		T removedData;
		if (head.getNext() == null) {
			removedData = head.getData();
			head = null;
			tail = null;
			return removedData;
		}
		Node<T> current = head;
		while (current.getNext().getNext() != null) {
			current = current.getNext();
		}
		removedData = current.getNext().getData();
		current.setNext(null);
		tail = current;

		return removedData;
	}

	/**
	 * Removes and returns the first occurrence of the specified value in the list.
	 * @param value the value to be removed from the list
	 * @return the removed value, or null if the value is null or not present in the list
	 */
	public T remove(T value){
		if (value == null || head == null) {
			return null;
		}
		size--;
		if (head.getData().equals(value)) {
			T removedData = head.getData();
			head = head.getNext();
			if (head == null) {
				tail = null;
			}
			return removedData;
		}
		Node<T> current = head;
		while (current.getNext() != null && !current.getNext().getData().equals(value)) {
			current = current.getNext();
		}
		if (current.getNext() == null) {
			return null;
		}
		T removedData = current.getNext().getData();
		current.setNext(current.getNext().getNext());
		if (current.getNext() == null) {
			tail = current;
		}

		return removedData; // Return the data of the removed node
	}

	/**
	 * Returns the index of the first occurrence of the specified value in the list.
	 * @param value the value to search for in the list
	 * @return the index of the first occurrence of the value, or -1 if the value is null or not present
	 */
	public int indexOf(T value){
		if (value == null || head == null) {
			return -1;
		}

		Node<T> current = head;
		int index = 0;
		while (current != null) {
			if (current.getData().equals(value)) {
				return index;
			}
			current = current.getNext();
			index++;
		}

		return -1;
	}


	/**
	 * Returns a string representing all values in the list, from beginning to end, separated by a single space.
	 * @return a string representing the values in the list
	 */
	public String listToString() {
		if (head == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		Node<T> current = head;
		while (current != null) {
			sb.append(current.getData());
			if (current.getNext() != null) {
				sb.append(" ");
			}
			current = current.getNext();
		}

		return sb.toString();
	}

	/**
	 * Returns the node containing the specified value.
	 * @param value the value to search for in the list
	 * @return the node containing the value, or null if the value is null or not present
	 */
	public Node<T> getNode(T value){
		if (value == null || head == null) {
			return null;
		}

		Node<T> current = head;
		while (current != null) {
			if (current.getData().equals(value)) {
				return current;
			}
			current = current.getNext();
		}

		return null;
	}

	/**
	 * Moves the node containing the specified value to the start of the list.
	 * @param value the value to move to the start of the list
	 * @return true if the move was successful, false otherwise
	 */
	public boolean moveToFront(T value){
		if (value == null || head == null) {
			return false;
		}

		if (head.getData().equals(value)) {
			return true;
		}

		Node<T> prev = null;
		Node<T> current = head;
		while (current != null && !current.getData().equals(value)) {
			prev = current;
			current = current.getNext();
		}

		if (current == null) {
			return false;
		}
		if (current.getNext() == null) {
			tail = prev;
		}
		if (prev != null) {
			prev.setNext(current.getNext());
		}  else {
			head = current.getNext();
		}
		current.setNext(head);
		head = current;

		return true;
	}

	/**
	 * Finds the node preceding the specified node in the list.
	 * @param node the node whose predecessor is to be found
	 * @return the node preceding the specified node, or null if the specified node is null or not found
	 */
	private Node<T> findNodeBefore(Node<T> node) {
		Node<T> current = head;
		while (current != null && current.getNext() != node) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Moves the node containing the specified value one location closer to the start of the list.
	 * @param value the value to move forward in the list
	 * @return true if the move was successful, false otherwise
	 */
	public boolean moveForward(T value){ //check time complexity
		if (value == null || head == null) {
			return false;
		}
		if (head.getData().equals(value)) {
			return true;
		}
		if(head.getNext() == null) {
			return false;
		}

		Node<T> prev = null;
		Node<T> current = head;
		while (current != null && !current.getData().equals(value)) {
			prev = current;
			current = current.getNext();
		}
		if (current == null) {
			return false;
		}
		if (current == tail) {
			tail = prev;
		}

		if (prev == head) {
			prev.setNext(current.getNext());
			current.setNext(prev);
			head = current;
			return true;
		}
		prev.setNext(current.getNext());
		findNodeBefore(prev).setNext(current);
		current.setNext(prev);
		return true;
	}

	/**
	 * Moves the node containing the specified value to the end of the list.
	 * @param value the value to move to the end of the list
	 * @return true if the move was successful, false otherwise
	 */
	public boolean moveToBack(T value){ //check time complexity
		if (value == null || head == null) {
			return false;
		}

		Node<T> prev = null;
		Node<T> current = head;
		while (current != null && !current.getData().equals(value)) {
			prev = current;
			current = current.getNext();
		}

		if (current == null || current.getNext() == null) {
			return false;
		}

		if (prev == null) {
			head = head.getNext();
		} else {
			prev.setNext(current.getNext());
		}

		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}

		last.setNext(current);
		current.setNext(null);
		tail = current;
		return true;
	}

	/**
	 * Moves the node containing the specified value one location closer to the end of the list.
	 * @param value the value to move backward in the list
	 * @return true if the move was successful, false otherwise
	 */
	public boolean moveBackward(T value){ //check time complexity
		if (value == null || head == null) {
			return false;
		}
		if (tail.getData().equals(value)) {
			return true;
		}
		if (head == tail) {
			return false;
		}

		Node<T> current = head;
		Node<T> next = current.getNext();
		while (next != null && !current.getData().equals(value)) {
			current = next;
			next = next.getNext();
		}
		if (next == null) {
			return false;
		}

		current.setNext(next.getNext());
		next.setNext(current);
		if(head != current) {
			findNodeBefore(current).setNext(next);
		} else {
			head = next;
		}
		if (current.getNext() == null) {
			tail = current;
		}
		return true;
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
		BasicList<String> list = new BasicList<>();

		// Test addFirst
		list.addFirst("Alice");
		list.addFirst("Bob");
		list.addFirst("Charlie");
		System.out.println("List after addFirst: " + list.listToString()); // Should print: Charlie Bob Alice

		// Test addLast
		list.addLast("David");
		list.addLast("Eve");
		list.addLast("Frank");
		System.out.println("List after addLast: " + list.listToString()); // Should print: Charlie Bob Alice David Eve Frank

		// Test removeFirst
		list.removeFirst();
		System.out.println("List after removeFirst: " + list.listToString()); // Should print: Bob Alice David Eve Frank

		// Test removeLast
		list.removeLast();
		System.out.println("List after removeLast: " + list.listToString()); // Should print: Bob Alice David Eve

		// Test remove
		list.remove("Bob");
		System.out.println("List after remove(\"Bob\"): " + list.listToString()); // Should print: Alice David Eve

		// Test indexOf
		System.out.println("Index of \"David\": " + list.indexOf("David")); // Should print: 1

		// Test moveToFront
		list.moveToFront("David");
		System.out.println("List after moveToFront(\"David\"): " + list.listToString()); // Should print: David Alice Eve

		// Test moveForward
		list.moveForward("Eve");
		System.out.println("List after moveForward(\"Eve\"): " + list.listToString()); // Should print: David Alice Eve

		// Test moveToBack
		list.moveToBack("David");
		System.out.println("List after moveToBack(\"David\"): " + list.listToString()); // Should print: Alice Eve David

		// Test moveBackward
		list.moveBackward("Alice");
		System.out.println("List after moveBackward(\"Alice\"): " + list.listToString()); // Should print: Alice David Eve
	
		//add more test cases by yourself!
		
	}
}