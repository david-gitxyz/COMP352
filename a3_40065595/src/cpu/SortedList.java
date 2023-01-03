package cpu;


/**
 * This class implements a priority queue with a sorted list
 * @author David Xie
 *
 */
public class SortedList extends AbstractPriorityQueue {
	
	/** Collection of entries */
	PositionalList<Job> list = new LinkedPositionalList<>();
	
	/** Insert a job and returns it */
	public Job insert(Job jobEntry) throws IllegalArgumentException {
		checkKey(jobEntry);
		Position<Job> walk = list.last();
		while (walk != null && compare(jobEntry, walk.getElement()) < 0)
			walk = list.before(walk);
		if (walk == null)
			list.addFirst(jobEntry); // new job is highest priority
		else
			list.addAfter(walk, jobEntry); // new job goes after walk
		return jobEntry;
	}
	
	/** Returns (but does not remove) a job with highest priority. */
	public Job min() {
		if (list.isEmpty()) 
			return null;
		return list.first().getElement();
	}
	
	/** Removes and returns a job with highest priority. */
	public Job removeMin() {
		if (list.isEmpty()) 
			return null;
		return list.remove(list.first());
	}

	/** Returns the number of items in the priority queue. */
	public int size( ) { return list.size( ); }


}
