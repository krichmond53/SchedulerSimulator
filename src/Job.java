
public class Job implements Comparable<Job>{
	
	private int number;					// Job Number
	private int execute;				// Time it takes to execute the job
	private int entry;					// Time slice when the job enters the system
	private int priority;				// Priority of the job (1 is highest, 4 is lowest)
	private int startTime;				// Time that the job entered the processor
	private int timeLeft;				// Time remaining to execute

	public Job(int number, int execute, int entry, int priority) {
		super();
		setNumber(number);
		setExecute(execute);
		setEntry(entry);
		setPriority(priority);
		timeLeft = execute;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void processOne() {
		timeLeft--;
	}

	/**
	 * @return the job number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the execute time for the job
	 */
	public int getExecute() {
		return execute;
	}

	/**
	 * @return the entry time for the job
	 */
	public int getEntry() {
		return entry;
	}

	/**
	 * @return the priority of the job
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * @param number - Sets the job number
	 */
	public void enterProcessor(int number) {
		startTime = number;
	}
	
	/**
	 * @return the time that the job entered the processor
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * @param number - Sets the job number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @param execute - Sets the execution time of the job
	 */
	public void setExecute(int execute) {
		this.execute = execute;
	}

	/**
	 * @param entry - Sets the entry time of the job
	 */
	public void setEntry(int entry) {
		this.entry = entry;
	}

	/**
	 * @param priority - Sets the priority of the job
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
	 *  Determines how this Job can be compared to other objects of the same type.
	 */
	public int compareTo(Job o){
		Job other = null;
		int result = 0;
		other = (Job) o;
		
		if (this.equals(other)) result = 0;
		else if (this.getPriority() > other.getPriority()) result = 1;
		else if (this.getPriority() < other.getPriority()) result = -1;
		else if (this.getPriority() == other.getPriority()) {
			if (this.getNumber() > other.getNumber()) result = 1;
			if (this.getNumber() < other.getNumber()) result = -1;
		}
		return result;

	 }
	
	/**
	 * toString method that has been overridden from Object
	 */
	public String toString(){
		return "Job #: " + getNumber() + "  CPU time: " + getExecute() + "  Start time: " + getEntry() 
    	+ "  Priority: " + getPriority()+ "  Time Left: " + getTimeLeft();
	}
	
	// Overrides the equals method inherited from Object
	public boolean equals(Object obj) {
		Job other;
			
		if (!(obj instanceof Job)) return false;
		else other = (Job) obj;
		if (this.number != other.getNumber() || this.execute != other.getExecute() ||
				this.entry != other.getEntry() || this.priority != other.getPriority()) {
		return false; 
		}
		return true;
	}
	
}
