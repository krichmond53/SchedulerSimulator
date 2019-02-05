import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Processor {
	private int clock;
	private PriorityQueue<Job> line = new PriorityQueue<Job>();
	private Stack<Job> active = new Stack<Job>();
	
	public Processor() {
		setClock();
	}
	
	public void setClock(){
		clock = 0;
	}
	
	public int getClock(){
		return clock;
	}
	
	public void tick(){
		clock++;
	}
	

	public void processJobs(ArrayList<Job> a){
		Job currentJob = null;
		int qWait = 0, stackWait = 0, numJobs = a.size();
		
		while (!active.isEmpty() || !line.isEmpty() || !a.isEmpty()){		// Continue while stack, queue, or array has a Job
			tick();	
			
			if (!a.isEmpty() && a.get(0).getEntry() == clock && line.isEmpty()){		// If the next job has an entry time equals to the clock...
				line.add(a.get(0));											// ... put it in the queue ...
				System.out.println(clock + ":  job number " + a.get(0).getNumber() + " (cpu: " + a.get(0).getExecute() + ") entering"
					+ " the system with a priority of " + a.get(0).getPriority());
				a.remove(0);												// ... and remove it from the array
			}
			
			if (!active.isEmpty() && active.peek().getTimeLeft() == 0){		// If the job on the stack is finished processing...
				Job end = active.pop();										// Pop it off, and leave the system
				stackWait += clock - currentJob.getStartTime() - currentJob.getExecute();
				qWait += clock - currentJob.getEntry() - currentJob.getExecute();
				System.out.println(clock + ":  job number " + end.getNumber() + " finishes processing\n" + clock + ":  job number "
						+ end.getNumber() + " leaves the system");

//				System.out.println("***Stack wait total: "+ stackWait + "   Queue wait total: " + qWait);
//				System.out.println("***Stack wait average: "+ (double) stackWait/(10-a.size()) + 
//						 "   Queue wait average: " + (double) (qWait-stackWait)/(10-a.size()));
				
				if (line.isEmpty() && active.isEmpty() && end.getTimeLeft() == 0 && a.isEmpty()) { break; }

			}
			
			if (!active.isEmpty() && !line.isEmpty()){						// If there's something in the stack and queue....
					if (active.peek().getPriority() <= line.peek().getPriority()){		// ... and the stack has a higher priority than the queue
						currentJob = active.pop();							// ... then the job from the stack moves to be processed
				}	else { 
					currentJob = line.remove();						// Otherwise, the job from the queue moves to be processed
					currentJob.enterProcessor(clock); 				// Set the time the job entered the processor
					System.out.println(clock + ":  job number " + currentJob.getNumber() + " begins processing (cpu: " + currentJob.getExecute() + ")");
				}
			}	else if	(active.isEmpty() && !line.isEmpty()) {				// If there's only something in the queue, move it to be processed
					currentJob = line.remove();
					currentJob.enterProcessor(clock);						// Set the time the job entered the processor 
					System.out.println(clock + ":  job number " + currentJob.getNumber() + " begins processing (cpu: " + currentJob.getExecute() + ")");
			}	else if	(!active.isEmpty() && line.isEmpty()) {				// If there's only something in the stack, move it to be processed
				currentJob = active.pop();
			}
			
			
			if (!a.isEmpty() && a.get(0).getEntry() == clock && !line.isEmpty()){		// If the next job has an entry time equals to the clock...
				line.add(a.get(0));											// ... put it in the queue ...
				System.out.println(clock + ":  job number " + a.get(0).getNumber() + " (cpu: " + a.get(0).getExecute() + ") entering"
					+ " the system with a priority of " + a.get(0).getPriority());
				a.remove(0);												// ... and remove it from the array
			}
			
			currentJob.processOne();										// Decrement execute time for processing this slice
			active.push(currentJob);										// Push the current job back onto the active job stack
//			System.out.println("*** " + clock + ":  " + currentJob);
			
		}
		System.out.println("\nAverage wait time in input queue: " + ((double) (qWait-stackWait)/numJobs));
		System.out.println("Average wait time in active job stack: " + ((double) stackWait/numJobs));
	}
}