import java.util.Iterator;

/**
 * Cache implementing FIFO (First-In, First-Out) replacement policy.
 */
public class FifoCache implements Cache {

	/**
	 * A variable that we have to keep track of the capacity of the list.
	 */
	private int capacity;

	/**
	 * The list for the cache.
	 */
	private BasicList<String> storage;

	/**
	 * Constructs a FIFO cache with the specified capacity.
	 * @param cap the capacity of the cache
	 * @throws IllegalArgumentException if cap is not positive
	 */
	public FifoCache(int cap){
		if(cap <= 0) {
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
		if (storage.getFirst() == null) {
			return null;
		}
		return storage.getFirst();
	}

	/**
	 * Determines whether the access to addr is a hit or miss.
	 * @param addr the address to be accessed
	 * @return true if the access is a hit, false if it's a miss
	 * @throws IllegalArgumentException if addr is null
	 */
	public boolean access(String addr){
		if (addr == null) {
			throw new IllegalArgumentException("Address cannot be null.");
		}
        for (String item : storage) {
            if (item.equals(addr)) {
                return true;
            }
        }
		if (isFull()) {
			storage.removeFirst();
		}
		storage.addLast(addr);
		return false;
	}

	/**
	 * Returns a string representing all items in the cache, following the order from first in to last in.
	 * @return a string representing all items in the cache
	 */
	@Override
	public String toString(){
		return storage.listToString();
	}
}	
