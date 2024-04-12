import java.util.Iterator;

/**
 * A basic implementation of a hash map.
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class BasicMap<K, V> {
	
	//******************************************************
	//*******     BELOW THIS LINE IS PROVIDED code   *******
	//*******             Do NOT edit code!          *******
	//*******		   Remember to add JavaDoc		 *******
	//******************************************************

	/**
	 * Internal class representing a key-value pair.
	 */
	private class Pair {

		/**
		 * The key of a key-value pair in the map.
		 */
		private K key;

		/**
		 * The value associated with a key in the map.
		 */
		private V value;

		/**
		 * Constructs a pair with the given key and value.
		 * @param key the key of the pair
		 * @param value the value of the pair
		 */
		public Pair(K key, V value){
			this.key = key;
			this.value = value;
		}

		/**
		 * Retrieves the key of this pair.
		 * @return the key of this pair
		 */
		public K getKey(){ return key; }

		/**
		 * Retrieves the value of this pair.
		 * @return the value of this pair
		 */
		public V getValue(){ return value; }

		/**
		 * Sets the value of this pair.
		 * @param value the value to set
		 */
		public void setValue(V value){ this.value = value; }

		/**
		 * Returns a string representation of this pair.
		 * @return a string representation of this pair
		 */
		@Override
		public String toString(){
			return "<"+key.toString()+":"+value.toString()+">";
		}

		/**
		 * Checks if this pair is equal to another object.
		 * @param o the object to compare
		 * @return true if the objects are equal, false otherwise
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			// return true if two pairs have matching keys
			// i.e. <"Alice", 1> is considered as equal to <"Alice", 2>
			if(o instanceof BasicMap<?,?>.Pair) {
				Pair pair = (Pair)o;
				return pair.key.equals(key);  
			}
			return false;
		}

		/**
		 * Returns the hash code of this pair.
		 * @return the hash code of this pair
		 */
		@Override
		public int hashCode(){
			return key.hashCode();
		}
		
	}

	/**
	 * An array of BasicList instances used as buckets in the hash map.
	 * Each list holds key-value pairs (Pairs) that hash to the same index.
	 */
	private BasicList<Pair>[] buckets;

	/**
	 * Will fix the capacity to 7.
	 */
	final static private int DEFAULT_CAPACITY = 7;

	/**
	 * Tracks how many elements in HashMap.
	 */
	private int size;

	/**
	 * Constructs a new BasicMap instance with the default capacity.
	 * Initializes the internal array of buckets and sets the size to 0.
	 */
	@SuppressWarnings("unchecked")
	public BasicMap() {
		buckets = (BasicList<Pair>[])new BasicList[DEFAULT_CAPACITY];
		size = 0;
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * @return the number of key-value mappings in this map
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the current capacity of the hash map.
	 * @return the number of buckets in the hash map
	 */
	private int capacity() {
		return buckets.length;
	}

	/**
	 * Returns the hash code for a given key.
	 * @param key the key whose hash code to compute
	 * @return the hash code for the key
	 */
	private int getHash(K key) {
		return Math.abs(key.hashCode());
	}

	/**
	 * Returns a string representation of this map for debugging purposes.
	 * @return a string representation of this map for debugging purposes
	 */
	public String toStringDebug() {
		//print all entries of buckets, including null ones
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<buckets.length; i++) {		
			BasicList<Pair> list = buckets[i];	
			sb.append("[");	
			if (list != null) {
				sb.append(list.listToString());
			}
			sb.append("]");
			if (i!=buckets.length-1)
				sb.append(",");	  

		}
		return "{" + sb.toString() + "}";
	}

	/**
	 * Returns a string representation of this map.
	 * @return a string representation of this map
	 */
	@Override
	public String toString() {
		//only display non-null entries of buckets
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<buckets.length; i++) {		
			BasicList<Pair> list = buckets[i];	
			if (list != null) {
				if (sb.length()>0)
					sb.append(",");	 
				sb.append(list.listToString());
			}
		}
		return sb.toString();
	}

	//******************************************************
	//*******     BELOW THIS LINE IS YOUR CODE       *******
	//******************************************************
	// ADD PRIVATE METHODS HERE IF NEEDED!
	// YOU CANNOT ADD MORE DATA MEMBERS

	/**
	 * Searches for a pair with the given key in the hash map.
	 * @param key the key to search for
	 * @return the pair associated with the key, or null if not found
	 */
	private Pair findPair(K key) {
		int index = getHash(key) % capacity();
		BasicList<Pair> list = buckets[index];
		if (list == null) {
			return null;
		}
		for (Pair pair : list) {
			if (pair.getKey().equals(key)) {
				return pair;
			}
		}
		return null;
	}

	/**
	 * Associates the specified value with the specified key in this map.
	 * @param key the key with which the specified value is to be associated
	 * @param value the value to be associated with the specified key
	 * @throws IllegalArgumentException if the key or value is null
	 */
	public void put(K key, V value) {
		// mapping key to value in the hashmap
		// - throws IllegalArgumentException for null key or null value (with any error msg)
		// - if key is new, add a new entry (key, value)
		// - if key is present, make sure (key, value) is the only mapping of key in hashtable
		
		// Note: Implement the hash table with separate chaining.
		// - when a new (key,value) pair is added, add it to the end of the chain
		// - if key is already present, you should just change its mapping. 
		//    do not remove the key then add it back, which is less efficient.
		
		// O(load) on average, and O(n) worst case
		if (key == null || value == null) {
			throw new IllegalArgumentException("Key or value cannot be null");
		}
		int index = getHash(key) % capacity();
		if (buckets[index] == null) {
			buckets[index] = new BasicList<>();
		}
		Pair newPair = new Pair(key, value);
		Pair existingPair = findPair(key);
		if (existingPair != null) {
			existingPair.setValue(value);
		} else {
			buckets[index].addLast(newPair);
			size++;
		}
    }

	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public V get(K key) {
		// return the current mapping of key
		// if key is null or not present, return null
		//	
		// O(load) on average, and O(n) worst case

		if (key == null) {
			return null;
		}
		Pair pair = findPair(key);
		return pair != null ? pair.getValue() : null;
	}

	/**
	 * Removes the mapping for a key from this map if it is present.
	 * @param key the key whose mapping is to be removed from the map
	 * @return the previous value associated with the key, or null if there was no mapping for the key
	 */
	public V delete(K key){
		// return the current mapping of key from hashmap and delete it
		// -if key is null or not present, return null
		// 
	
		// O(load) on average, and O(n) worst case

		if (key == null) {
			return null;
		}
		int index = getHash(key) % capacity();
		if (buckets[index] == null) {
			return null;
		}
		Pair pair = findPair(key);
		if (pair != null) {
			buckets[index].remove(pair);
			size--;
			return pair.getValue();
		}
		return null;
	}
	
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//*******		Remember to add JavaDoc			 *******
	//******************************************************

	/**
	 * Main method I used to test this class.
	 * @param args args used from command line.
	 */
	public static void main(String args[]) {
		BasicMap<String, Integer> map = new BasicMap<>();

		// Testing put() method
		map.put("apple", 10);
		map.put("banana", 20);
		map.put("orange", 30);
		map.put("grape", 40);
		map.put("apple", 50); // Updating the value for existing key

		// Testing get() method
		System.out.println("Value for key 'banana': " + map.get("banana")); // Should print 20
		System.out.println("Value for key 'apple': " + map.get("apple"));   // Should print 50
		System.out.println("Value for key 'grape': " + map.get("grape"));   // Should print 40
		System.out.println("Value for key 'watermelon': " + map.get("watermelon")); // Should print null

		// Testing delete() method
		System.out.println("Deleted value for key 'banana': " + map.delete("banana")); // Should print 20
		System.out.println("Deleted value for key 'apple': " + map.delete("apple"));   // Should print 50
		System.out.println("Deleted value for key 'grape': " + map.delete("grape"));   // Should print 40
		System.out.println("Deleted value for key 'watermelon': " + map.delete("watermelon")); // Should print null

		// Testing size() method
		System.out.println("Size of the map after deletions: " + map.size()); // Should print 1
		/*BasicMap<String, String> map = new BasicMap<>();

		map.put("apple", "red");
		map.put("pear", "yellow");
		map.put("eggplant", "purple");
		
		if (map.get("apple").equals("red") && map.get("eggplant").equals("purple") && map.size() == 3){
			System.out.println("Yay1");
		}
		
		//change mapping, delete
		map.put("apple", "green");
		if (map.get("apple").equals("green") && map.size()==3 && map.delete("pear").equals("yellow") 
			&& map.size() == 2) {
			System.out.println("Yay2");
		}
		
		//key not present
		if (map.get("banana")==null && map.delete("pear")==null){
			System.out.println("Yay3");		
		}
		
		//add to tail
		map.put("cherry", "red");
		if (map.toStringDebug().equals("{[],[<apple:green> <cherry:red>],[],[],[],[<eggplant:purple>],[]}")){
			System.out.println("Yay4");		
		}

		 */
		
	}

}