package cpu;

public class Job {
	
	private String jobName; //name of the process/job
	private int jobLength; //needed cpu cycles for this job to terminate
	private int currentJobLength; //remaining length of the job at any given time
	private int jobPriority; //initial priority of the job
	private int finalPriority; //final priority of the job at termination time
	private long entryTime; //time this job entered priority queue
	private long endTime; //time when job is terminated
	private long waitTime; //total wait time
	
	public Job(String name, int len, int currentLen, int jobPrio, int finalPrio, long entry, long end, long wait) {
		jobName = name;
		jobLength = len;
		currentJobLength = currentLen;
		jobPriority = jobPrio;
		finalPriority = finalPrio;
		entryTime = entry;
		endTime = end;
		waitTime = wait;
	}
	public Job(Job copy) {
		this(copy.getName(), copy.getLen(), copy.getCurrentLen(), copy.getInitialPrio(), copy.getPriority(), copy.getEntryTime(), 
				copy.getEndTime(), copy.getWaitTime());
	}
	
	//mutators
	public void setName(String newName) {
		jobName = newName;
	}
	public void setLength(int length) {
		jobLength = length;
	}
	public void setCurrentLength(int length) {
		currentJobLength = length;
	}
	public void setInitialPriority(int prio) {
		jobPriority = prio;
	}
	public void setFinalPriority(int prio) {
		finalPriority = prio;
	}
	public void setEntryTime(long time) {
		entryTime = time;
	}
	public void setWaitTime(long time) {
		waitTime = time;
	}
	public void setEndTime(long time) {
		endTime = time;
	}
	
	//accessors
	public String getName() {
		return jobName;
	}
	public int getLen() {
		return jobLength;
	}
	public int getCurrentLen() {
		return currentJobLength;
	}
	public int getInitialPrio() {
		return jobPriority;
	}
	public int getPriority() {
		return finalPriority;
	}
	public long getEntryTime() {
		return entryTime;
	}
	public long getWaitTime() {
		return waitTime;
	}
	public long getEndTime() {
		return endTime;
	}
	
	//string return
	public String toString() {
		return "Now executing " + jobName + ". Job length: " + jobLength + " cycles; Current remaining length: " 
				+ currentJobLength + " cycles; Initial priority: " + jobPriority + "; Current "
						+ "priority: " + finalPriority + ".";
	}
}
