import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class Project5 {
	public static void main(String[] args) throws FileNotFoundException{
//		printHeading(5, "Simulation");
		String inString = commandLine(args);		// Method for checking command line
		File goodFile = isValidFile(inString);		// Method for validating a file
	    	    
	    Scanner readIt = new Scanner(goodFile);		// Create a scanner to read the file
	    ArrayList<Job> jobs = makeJobs(readIt);		// Method to make ArrayList of jobs from the scanner

	    Processor p = new Processor();
	    p.processJobs(jobs);

	    
	}
	
	
	public static ArrayList<Job> makeJobs(Scanner input){
		ArrayList<Job> j = new ArrayList<Job>();
		while (input.hasNextLine()){
			String thisLine = input.nextLine();
			Scanner getNums = new Scanner(thisLine);
			int jobNumber = 0;
			int executeTime = 0;
			int entryTime = 0;
			int jobPriority = 0;
			boolean badInfo = false;
			
			if (getNums.hasNextInt()) jobNumber = getNums.nextInt();		// Next integer will be job number
			else badInfo = true;
			if (jobNumber < 1) badInfo = true;
			if (!j.isEmpty() && (jobNumber <= j.get(j.size()-1).getNumber())) badInfo = true;
			
			if (getNums.hasNextInt()) executeTime = getNums.nextInt();		// Next integer will be execute time
			else badInfo = true;
			if (executeTime < 1) badInfo = true;

			if (getNums.hasNextInt()) entryTime = getNums.nextInt();		// Next integer will be entry time
			else badInfo = true;
			if (entryTime < 1) badInfo = true;
//			if (!j.isEmpty()) System.out.println(j.get(j.size()-1).getEntry());
			if (!j.isEmpty() && entryTime <= j.get(j.size()-1).getEntry()) badInfo = true;
				
			if (getNums.hasNextInt()) jobPriority = getNums.nextInt();		// Next number will be job priority
			else badInfo = true;
			if (jobPriority < 1 || jobPriority > 4) badInfo = true;
			
			if (getNums.hasNext())	badInfo = true;							// If there's more info, it is invalid
		
			if (!badInfo) {													// When there's no bad information....
				Job work = new Job(jobNumber, executeTime, entryTime, jobPriority);			// ...create a new job
				j.add(work);
			} else System.out.println("ERROR: Invalid infomation on line >>> " + thisLine + " <<<");
		getNums.close();
		}
		return j;
	}
	public static String commandLine(String[] args){
	    String s;
	    Scanner keyIn;
		if (args.length > 0) {					// Accept a command line argument
	    	s = args[0];						// Create a new inFile that is valid
	    } else {								// If no command line argument
	    	keyIn = new Scanner(System.in);
		    System.out.println("Please enter the file name to read from: ");
		   s = keyIn.nextLine();				// Create a new inFIle that is valid
	    }
		return s;
	}
	public static File isValidFile(String userFile){
		File inFile = new File(userFile);
		while(!inFile.exists() || inFile.isDirectory()){ 
			System.out.println("Please enter a valid file name or Q to quit.");
			Scanner in = new Scanner(System.in);
			String validFile = in.nextLine();
			if(validFile.equalsIgnoreCase("q")){   
				System.out.println("\nProgram terminated by user.");
				in.close();
				return null;
			} else inFile = new File(validFile);
			if (inFile.exists()) in.close();
		}
	  return inFile;
	}
	private static void printHeading(int projectNumber, String projectName)
	  {
	    System.out.println("Kevin Richmond");
	    System.out.println("CSC 202, Spring 2016");
	    System.out.println("Project " + projectNumber);
	    System.out.println(projectName + "\n");
	  }
}
