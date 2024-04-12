// TO DO: add your implementation and JavaDocs.
import java.util.Iterator;

/**
 * Cache implementing LRU (Least Recently Used) replacement policy.
 */
public class LruCache implements Cache {

	/**
	 * The max number of items in the cache.
	 */
	private int capacity;

	/**
	 * Where the LRU cache is stored.
	 */
	private BasicList<String> storage; //each address is a string

	/**
	 * Constructs an LRU cache with the specified capacity.
	 * @param cap the capacity of the cache
	 * @throws IllegalArgumentException if cap is not positive
	 */
	public LruCache(int cap){
		if (cap <= 0) {
			throw new IllegalArgumentException("Capacity cannot be negative.");
		}
		this.capacity = cap;
		this.storage = new BasicList<>();
	}

	/**
	 * Checks if the cache is full.
	 * @return true if the cache is full, false otherwise
	 */
	public boolean isFull(){
		return storage.size() == capacity;
	}


	/**
	 * Reports the maximum number of items allowed in the cache.
	 * @return the capacity of the cache
	 */
	public int capacity(){
		return capacity;
	}

	/**
	 * Reports the number of items stored in the cache.
	 * @return the number of items stored in the cache
	 */
	public int size(){
		return storage.size();
	}

	/**
	 * Returns the item that will be evicted if the next access is a miss.
	 * @return the item to be evicted, or null if no item will be evicted
	 */
	public String nextToReplace(){
		if (storage.size() == 0) {
			return null;
		} else {
			return storage.getLast();
		}
	}

	/**
	 * Determines whether the access to addr is a hit or miss.
	 * Performs necessary updating to maintain the LRU cache.
	 * @param addr the address to be accessed
	 * @return true if the access is a hit, false if it's a miss
	 * @throws IllegalArgumentException if addr is null
	 */
	public boolean access(String addr){
		if (addr == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		boolean hit = false;
		for (String item : storage) {
			if (item.equals(addr)) {
				hit = true;
				break;
			}
		}
		if (hit) {
			storage.remove(addr);
			storage.addLast(addr);
		} else {
			if (isFull()) {
				storage.removeFirst();
			}
			storage.addLast(addr);
		}
		return hit;
	}

	/**
	 * Returns a string representing all items in the cache, following the order from LRU to MRU.
	 * @return a string representing all items in the cache
	 */
	@Override
	public String toString(){
		return storage.listToString();
	}
}
