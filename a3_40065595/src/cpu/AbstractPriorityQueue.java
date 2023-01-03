package cpu;

import java.util.Comparator;

/**
 * Abstract class for the priority queue method implementations
 * @author David Xie
 *
 */
public abstract class AbstractPriorityQueue implements PriorityQueue {
	
		/** Implement compare method for the keys. */
		protected int compare(Job a, Job b) {
			return new Integer(a.getPriority()).compareTo(b.getPriority());
		}
		
		/** Determines whether a key is valid. */
		protected boolean checkKey(Job jobEntry) throws IllegalArgumentException {
			try {
				return (compare(jobEntry,jobEntry) == 0); // see if key can be compared to itself
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("Incompatible key");
			}
		}
		/** Tests whether the priority queue is empty. */
		public boolean isEmpty( ) { return size( ) == 0; }

}
