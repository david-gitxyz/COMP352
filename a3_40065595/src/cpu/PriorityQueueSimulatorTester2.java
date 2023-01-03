package cpu;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a CPU that executes a number of jobs using a priority queue of array-list-based heap
 * @author David Xie
 *
 */
public class PriorityQueueSimulatorTester2 {
	
	/** check starvation for sorted list */
	public static int checkStarve(HeapPriorityQueue newSet) {
		Job starved = newSet.heap.get(0);
		int starvedIndex = 0;
		for(int i = 0; i < newSet.size(); i++) {
			if(new Long(starved.getEntryTime()).compareTo(new Long(newSet.heap.get(i).getEntryTime())) < 0 && 
					newSet.heap.get(i).getLen() == newSet.heap.get(i).getCurrentLen()) {
				starved = newSet.heap.get(i);
				starvedIndex = i;
			}
		}
		if(starved != newSet.heap.get(0)) {
			starved.setFinalPriority(1);
			newSet.heap.remove(starvedIndex);
			newSet.insert(starved);
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) {
		
		int maxNumberOfJobs = 100;
		Job[] jobsInputArray = new Job[maxNumberOfJobs];
		
		for(int i = 0; i < maxNumberOfJobs; i++) {
			int rand1 = ThreadLocalRandom.current().nextInt(1, 71); //random number for length
			int rand2 = ThreadLocalRandom.current().nextInt(1, 41); //random number for priority
			
			jobsInputArray[i] = new Job("JOB_"+(i+1), rand1, rand1, rand2, rand2, 0, 0, 0);
		}
				
		HeapPriorityQueue newSet = new HeapPriorityQueue();
		
		long currentTime = 0; //tracking current time
		long startTime = System.currentTimeMillis(); //start recording the time now
		//inserting elements from array to PQ
		for(int i = 0; i < maxNumberOfJobs; i++) {
			jobsInputArray[i].setEntryTime(newSet.size() + 1); //update entry time
			newSet.insert(jobsInputArray[i]);
			currentTime++;
		}
		
		Job executed;
		int terminateCounter = 0;
		int prioChanges = 0;
		long sumWait = 0;
		while(newSet != null) {
			//execution
			executed = newSet.removeMin();
			executed.setCurrentLength(executed.getCurrentLen() - 1);
			System.out.println(executed.toString());
			++currentTime;
			
			//if not done yet
			if(executed.getCurrentLen() > 0)
				newSet.insert(executed);
			
			//if ready for termination
			if(executed.getCurrentLen() == 0) {
				executed.setEndTime(currentTime);
				executed.setWaitTime(executed.getEndTime() - executed.getEntryTime() - executed.getLen());
				sumWait += executed.getWaitTime();
				++terminateCounter;
			}
			
			//check for starvation
			if(terminateCounter % 30 == 0 && terminateCounter > 0) {
				prioChanges += checkStarve(newSet);
				++currentTime;
			}
			
			if(newSet.min() == null)
				break;
		}
		long averageWait = sumWait/terminateCounter;
		long endTime = System.currentTimeMillis(); //finish recording the time now.
		long actualTime = endTime - startTime;
		System.out.println();
		
		//Report
		System.out.println("Current system time (cycles): " + currentTime + "\n"
				+ "Total number of jobs executed: " + terminateCounter + " jobs\n"
				+ "Average process waiting time: " + averageWait + " cycles\n"
				+ "Total number of priority changes: " + prioChanges + "\n"
				+ "Actual system time needed to execute all jobs: " + actualTime + " ms");

	}

}
