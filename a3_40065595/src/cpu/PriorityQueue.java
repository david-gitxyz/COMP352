package cpu;

/**
 * This interface is to implement a priority queue
 * @author David Xie
 *
 */
public interface PriorityQueue {
	
	int size();
	boolean isEmpty();
	Job insert(Job jobEntry) throws IllegalArgumentException;
	Job min();
	Job removeMin();
	

}
