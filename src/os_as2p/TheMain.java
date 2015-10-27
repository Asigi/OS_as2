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
		
		
	}
	
	
	/**
	 * This method creates the processes to be run on the processor.
	 */
	public static void createProcesses() {
		
		Random rand = new Random();
		myProcessAmount = rand.nextInt(60) + 100;
		
		for (int i = 0; i < LEVELS; i++) { //initialize all levels
			myList[i] = new PriorityList();
		}
		
		for (int i = 0; i < LEVELS ; i ++) { //create priority 1 and priority 2 processes.
			myList[i].enqueue(new Process(i));
		}
		
		//create many level 2 processes
		//some of these processes will randomly be "niced" and dropped to lower priority.
		
		
		
		
		
	}
}
