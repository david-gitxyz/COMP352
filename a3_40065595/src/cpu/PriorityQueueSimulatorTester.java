package cpu;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a CPU executing a number of jobs, using a priority queue of sorted list and of array-list-based heap
 * @author David Xie
 *
 */
public class PriorityQueueSimulatorTester {
	
	/** check starvation for sorted list */
	public static int checkStarveSorted(SortedList sorted) {
		Job starved = sorted.list.first().getElement();
		Position<Job> walk = sorted.list.first();
		Position<Job> starvedIndex = walk;
		while(walk != null) {
			if(new Long(starved.getEntryTime()).compareTo(new Long(walk.getElement().getEntryTime())) < 0 && 
					walk.getElement().getLen() == walk.getElement().getCurrentLen()) {
				starved = walk.getElement();
				starvedIndex = walk;
			}
			walk = sorted.list.after(walk);
		}
		if(starved != sorted.list.first().getElement()) {
			starved.setFinalPriority(1);
			sorted.list.remove(starvedIndex);
			sorted.insert(starved);
			return 1;
		}
		return 0;
	}
	
	/** check starvation for sorted list */
	public static int checkStarveHeap(HeapPriorityQueue newSet) {
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
		
		Job[] jobsInputArray2 = new Job[maxNumberOfJobs];
		for(int i = 0; i < maxNumberOfJobs; i++) {
			jobsInputArray2[i] = new Job(jobsInputArray[i]);
		}
		
		//FIRST: Sorted list
		SortedList newList = new SortedList();
		
		long currentTime = 0; //tracking current time
		long startTime = System.currentTimeMillis(); //start recording the time now
		//inserting elements from array to PQ
		for(int i = 0; i < maxNumberOfJobs; i++) {
			jobsInputArray[i].setEntryTime(newList.size() + 1); //update entry time
			newList.insert(jobsInputArray[i]);
			currentTime++;
		}
		
		Job executed;
		int terminateCounter = 0; //counter for terminated jobs
		int prioChanges = 0; //count of priority changes
		long sumWait = 0; //sum of waiting times
		while(newList != null) {
			//execution
			executed = newList.removeMin();
			executed.setCurrentLength(executed.getCurrentLen() - 1);
			System.out.println(executed.toString());
			++currentTime;
			
			//if not done yet
			if(executed.getCurrentLen() > 0)
				newList.insert(executed);
			
			//if ready for termination
			if(executed.getCurrentLen() == 0) {
				executed.setEndTime(currentTime);
				executed.setWaitTime(executed.getEndTime() - executed.getEntryTime() - executed.getLen());
				sumWait += executed.getWaitTime();
				++terminateCounter;
			}
			
			//check for starvation
			if(terminateCounter % 30 == 0 && terminateCounter > 0) {
				prioChanges += checkStarveSorted(newList);
				++currentTime;
			}
			
			if(newList.min() == null)
				break;
		}
		long averageWait = sumWait/terminateCounter; //average waiting time
		long endTime = System.currentTimeMillis(); //finish recording the time now.
		long actualTime = endTime - startTime; //actual time of execution
		System.out.println();
		
		//Report
		System.out.println("Current system time (cycles): " + currentTime + "\n"
				+ "Total number of jobs executed: " + terminateCounter + " jobs\n"
				+ "Average process waiting time: " + averageWait + " cycles\n"
				+ "Total number of priority changes: " + prioChanges + "\n"
				+ "Actual system time needed to execute all jobs: " + actualTime + " ms");
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("\nTake this time to record. Press any input to continue to heap experiment. ");
		String next = keyboard.nextLine();
		
		//SECOND: Array-list Heap
		HeapPriorityQueue newSet = new HeapPriorityQueue();
		currentTime = 0; //reset current time
		startTime = System.currentTimeMillis(); //reset recording the time
		//inserting elements from array to PQ
		for(int i = 0; i < maxNumberOfJobs; i++) {
			jobsInputArray2[i].setEntryTime(newSet.size() + 1); //update entry time
			newSet.insert(jobsInputArray2[i]);
			currentTime++;
		}
		terminateCounter = 0; //reset counter for terminated jobs
		prioChanges = 0; //reset counter for priority changes
		sumWait = 0; //reset sum of waiting times
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
				prioChanges += checkStarveHeap(newSet);
				++currentTime;
			}
			
			if(newSet.min() == null)
				break;
		}
		averageWait = sumWait/terminateCounter;
		endTime = System.currentTimeMillis();
		actualTime = endTime - startTime;
		System.out.println();
		
		//Report
		System.out.println("Current system time (cycles): " + currentTime + "\n"
				+ "Total number of jobs executed: " + terminateCounter + " jobs\n"
				+ "Average process waiting time: " + averageWait + " cycles\n"
				+ "Total number of priority changes: " + prioChanges + "\n"
				+ "Actual system time needed to execute all jobs: " + actualTime + " ms");


	}

}
