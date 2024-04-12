import java.util.Iterator;

/**
 * Cache implementing LFU (Least Frequently Used) replacement policy.
 */
public class LfuCache implements Cache { 

	//******************************************************
	//*******     BELOW THIS LINE IS PROVIDED code   *******
	//*******             Do NOT edit code!          *******
	//*******		   Remember to add JavaDoc		 *******
	//******************************************************

	/**
	 * Internal class representing a block in the cache.
	 * Each block stores the data item and the number of times it has been accessed.
	 */
	private class Block implements Comparable<Block> {

		/**
		 * Data item to store in cache.
		 */
		private String data;

		/**
		 * How many times this item is accessed since it is loaded in.
		 */
		private int count;

		/**
		 * Constructs a block with the given data item.
		 * @param addr the data item to store
		 */
		public Block(String addr){
			this.data = addr;
			count = 1;
		}

		/**
		 * Retrieves the access count of this block.
		 * @return the access count of this block
		 */
		public int getCount(){
			return count;
		}

		/**
		 * Increments the access count of this block.
		 */
		public void incCount(){
			count++;
		}

		/**
		 * Retrieves the data item stored in this block.
		 * @return the data item stored in this block
		 */
		public String getData(){
			return data;
		}

		/**
		 * Checks if this block is equal to another object.
		 * @param other the object to compare
		 * @return true if the objects are equal, false otherwise
		 */
		@Override
		public boolean equals(Object other){
			if (other instanceof Block){
				if ( ((Block)other).data.equals(this.data) ){
            		return true;
        		}
    		}
   			return false;		
		}

		/**
		 * Compares this block to another block based on their access counts.
		 * @param other the block to compare
		 * @return a negative integer, zero, or a positive integer as this block
		 *         is less than, equal to, or greater than the specified block
		 */
		public int compareTo(Block other){
			return this.count - other.count;			
		}

		/**
		 * Returns a string representation of this block including its data item and access count.
		 * @return a string representation of this block
		 */
		@Override
		public String toString(){
			return "<"+data.toString()+","+count+">";
		}
	}

	/**
	 * Max number of items allowed in the list.
	 */
	private int capacity;

	/**
	 * Where the cache is stored in a list.
	 */
	private SortedList<Block> storage; //NOTE: SortedList! List of Blocks! 

	//******************************************************
	//*******    	END of PROVIDED Code 	 		 *******
	//*******    	Do NOT Change PROVIDED Code 	 *******
	//******************************************************

	/**
	 * Constructs an LFU cache with the specified capacity.
	 * @param cap the capacity of the cache
	 * @throws IllegalArgumentException if cap is not positive
	 */
	public LfuCache(int cap){
		if (cap <= 0) {
			throw new IllegalArgumentException("Capacity cannot be negative.");
		}
		this.capacity = cap;
		this.storage = new SortedList<>();
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
		return storage.getFirst().getData();
	}

	/**
	 * Determines whether the access to addr is a hit or miss.
	 * Performs necessary updating to maintain the LFU cache.
	 * @param addr the address to be accessed
	 * @return true if the access is a hit, false if it's a miss
	 * @throws IllegalArgumentException if addr is null
	 */
	public boolean access(String addr){
		if (addr == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		Block target = null;
		for (Block block : storage) {
			if (block.getData().equals(addr)) {
				target = block;
				break;
			}
		}
		if (target != null) {
			target.incCount();
			storage.remove(target);
			storage.add(target);
			return true;
		} else {
			if (isFull()) {
				storage.removeFirst();
			}
			storage.add(new Block(addr));
			return false;
		}
	}


	/**
	 * Returns a string representing all items in the cache, following the order from LFU to MFU.
	 * If there is a tie, items should be included from LRU to MRU.
	 * @return a string representing all items in the cache
	 */
	@Override
	public String toString(){
		return storage.listToString();
	}
}