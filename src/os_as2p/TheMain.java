package os_as2p;

import java.util.Random;


/**
 * 
 * @author arshdeep
 *
 * TCSS422 Operating Systems
 * 
 * This is the second assignment for the class.
 */
public class TheMain {
	
	/**
	 * The number of levels.
	 */
	private static final int LEVELS = 6;
	
	/**
	 * The amount of time that each process gets on the processor before being
	 * removed so that the next process can run.
	 */
	private static final int TIME_SLICE = 50;
	
	
	/**
	 * The number of processes to be created and run.
	 */
	private static int myProcessAmount;
	
	/**
	 * The list of all the priority levels.
	 */
	private static PriorityList[] myList = new PriorityList[LEVELS];
	
	
	/**
	 * Main method.
	 * @param args stuff
	 */
	public static void main(String[] args) {
		
		createProcesses();
		
		handleProcesses();
		
	}
	
	/**
	 * This method processes the processes. 
	 * 
	 */
	private static void handleProcesses() {
		
		
		
		
		
		
		//Timeslices will be used, so decrement the run time of the process.
	}
	
	
	/**
	 * This method creates the processes to be run on the processor.
	 */
	public static void createProcesses() {
		
		Random rand = new Random();
		myProcessAmount = rand.nextInt(60) + 100; //decide how many total processes there will be.
		
		for (int i = 0; i < LEVELS; i++) { //initialize all levels
			myList[i] = new PriorityList();
		}
		
		int randomNumber = 5 + rand.nextInt(3); //this value is used to decide how many priority 0 and 1 processes there will be.
		
		for (int i = 0; i < randomNumber / 2; i++) { //create priority 0 processes.
			myList[0].enqueue(new Process(i, 10 + rand.nextInt(80)));  
		}
		for (int i = randomNumber / 2; i < randomNumber ; i++) { //create priority 1 processes.
			myList[1].enqueue(new Process(i, 15 + rand.nextInt(100)));  
		}
		for (int i = randomNumber; i < myProcessAmount ; i ++) { //create priority 2 (and some even lower level than that)
			
			Process process = new Process(i, 20 + rand.nextInt(220));
			
			int nicerRand = rand.nextInt(10); //helps decide if a process should be "niced".
			if (nicerRand < 7) { //most processes will go to priority 2.
				myList[2].enqueue(process);  
			} else { //a few processes will go into the lower levels.
				nicerRand = 3 + rand.nextInt(LEVELS - 3); //priority levels 3 through one number less than (the total levels minus 3).
				myList[nicerRand].enqueue(process); 
			}
		}
	}
}
