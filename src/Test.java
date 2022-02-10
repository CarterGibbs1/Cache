import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Test class for testing a Cache implementation
 * @author carter gibbs
 *
 */
public class Test {
	
	/**
	 * main method to parse arguments and run a one-level or two-level cache.
	 * @param args - arguments of the command line
	 * @throws FileNotFoundException - if the file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//double time1 = System.currentTimeMillis();
		Cache<String> cache1 = new Cache<String>(Integer.parseInt(args[1]));
		// if it is a two-level cache
		if (Integer.parseInt(args[0]) == 2) {
			Cache<String> cache2 = new Cache<String>(Integer.parseInt(args[2]));
			readFile(args[3], cache1, cache2);
			outputResults(args, cache1, cache2);
		} else {   // if it is only a one-level cache
			readFile(args[2], cache1);
			System.out.printf("First level cache with %s entries has been created\n", args[1]);
			outputResults(cache1);
		}
		//double time2 = System.currentTimeMillis();
		//System.out.println(time2-time1);
	}
	
	/**
	 * method to read in the tokens of a file, method is used for a one-level cache
	 * @param fileName - file name that is being read in
	 * @param cache - cache instance
	 * @throws FileNotFoundException - throws is file is not found
	 */
	public static void readFile(String fileName, Cache<String> cache) throws FileNotFoundException {
		Scanner readingFile = new Scanner(new File(fileName));
		while(readingFile.hasNext()) {
			String word = readingFile.next();
			cache.getObject(word);
		}
		readingFile.close();
	}

	/**
	 * method to read in the tokens of a file, method is used for a two-level cache
	 * @param fileName - file name that is being read in
	 * @param cache1 - first level cache instance
	 * @param cache2 - second level cache instance
	 * @throws FileNotFoundException - throws is file is not found
	 */
	public static void readFile(String fileName, Cache<String> cache1, Cache<String> cache2) throws FileNotFoundException {
		Scanner readingFile = new Scanner(new File(fileName));
		while(readingFile.hasNext()) {
			String word = readingFile.next();
			if (cache1.getObject(word) != null) {
				cache2.addObject(word);
			} else {
				cache2.getObject(word);
				cache1.addObject(word);
			}
		}
		readingFile.close();
	}
	
	/**
	 * prints the results of the cache search to the console. This is for a one-level cache.
	 * @param cache - cache instance
	 */
	public static void outputResults(Cache<String> cache1) {
		System.out.printf("The number of 1st-level references: %d\n", cache1.getTotalRequests());
		System.out.printf("The number of 1st-level cache hits: %d\n", cache1.getCacheHits());
		System.out.printf("The 1st-level cache hit ratio: %f\n", 1.0 * cache1.getCacheHits() / cache1.getTotalRequests());
		System.out.println();
	}
	
	/**
	 * prints the results of the cache search to the console.
	 * Overrides the outputResults(Cache<String>) for a two-level cache instance.
	 * @param cache1 - first level cache instance
	 * @param cache2 - second level cache instance
	 */
	public static void outputResults(String[] arguments, Cache<String> cache1, Cache<String> cache2) {
		System.out.printf("First level cache with %s entries has been created\n", arguments[1]);
		System.out.printf("Second level cache with %s entries has been created\n", arguments[2]);
		System.out.println();
		System.out.printf("The number of global references: %d\n", cache1.getTotalRequests());
		System.out.printf("The number of global cache hits: %d\n", cache1.getCacheHits() + cache2.getCacheHits());
		System.out.printf("The global hit ratio: %f\n", (1.0 * cache1.getCacheHits() + cache2.getCacheHits()) / cache1.getTotalRequests());
		System.out.println();
		outputResults(cache1);
		System.out.printf("The number of 2nd-level references: %d\n", cache2.getTotalRequests());
		System.out.printf("The number of 2nd-level cache hits: %d\n", cache2.getCacheHits());
		System.out.printf("The 2nd-level cache hit ratio: %f\n", 1.0 * cache2.getCacheHits()/ cache2.getTotalRequests());
	}
}
