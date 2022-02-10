import java.util.LinkedList;

/**
 * Cache class implementation using a double linked list.
 * @author carte
 *
 * @param <T> - generics
 */
public class Cache<T> {
	
	private LinkedList<T> list; // actual linked list, stores generics
	private final int MAX_SIZE; // max list size constant
	
	private int cacheHits; // total cache hits
	private int totalRequests; // total cache requests
	
	/**
	 * Constructor that takes in the max size of the cache
	 * @param maxSize
	 */
	public Cache(int maxSize) {
		list = new LinkedList<T>();
		MAX_SIZE = maxSize;
		cacheHits = 0;
		totalRequests = 0;
	}
	
	/**
	 * method to get object from the cache. Will look through the cache and increase total cache hits
	 * if the element is already in cache
	 * @param element - object we are trying to find
	 * @return element if it is found, null if not found.
	 */
	public T getObject(T element) {
		totalRequests++;
		if (addObject(element)) {
			cacheHits++;
			return element;
		}
		return null;
	}
	
	/**
	 * method to add an object to the top of the cache
	 * @param element - element that we are trying to add to the cache
	 * @return - a boolean if the element is already in the list
	 */
	public boolean addObject(T element) {
		boolean isFound = removeObject(element);
		if (list.size() >= MAX_SIZE) {
			list.removeLast();
		} 
		list.addFirst(element);
		return isFound;
	}

	/**
	 * method to remove an object from the cache
	 * @param element - object we want to remove
	 * @return - returns a boolean if the object was in the list
	 */
	public boolean removeObject(T element) {
		return list.remove(element);
	}
	
	/**
	 * clears cache
	 */
	public void clearCache() {
		list.clear();
	}
	
	/**
	 * returns the number of cache hits
	 */
	public int getCacheHits() {
		return cacheHits;
	}
	
	/**
	 * returns the number of cache requests
	 */
	public int getTotalRequests() {
		return totalRequests;
	}
}
