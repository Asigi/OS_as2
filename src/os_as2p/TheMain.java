package os_as2p;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



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
	private static final int LEVELS = 10;
	
	/**
	 * The amount of time that each process gets on the processor before being
	 * removed so that the next process can run.
	 */
	private static final int TIME_SLICE = 50;
	
	/**
	 * This represents a single millisecond.
	 */
	private static final int ONE_MILISEC = 1;
	
	/**
	 * The amount of time after which the processor will starts.
	 * In milliseconds.
	 */
	private static final int DELAY_TIME = 4000;
	
	/**
	 * The number of processes to be created and run.
	 */
	private static int myProcessAmount;
	
	/**
	 * The list of all the priority levels.
	 */
	private static PriorityList[] myList = new PriorityList[LEVELS];
	
	/**
	 * The current priority level which contains the process to be processed.
	 */
	private static int  currentPrioLvl = 0;

	/**
	 * The current process that is being worked on.
	 */
	private static Process currentProcess;
	
	/** 
	 * Whether or not the current process has finished and a new process needs to be loaded.
	 */
	private static boolean curProcFinish = true;
	
	/**
	 * Whether or not all of the processes have been finished.
	 */
	private static boolean finished = false;

	
	/**
	 * The number of runs that have been made.
	 */
	private static int runs = 0;
	
	/**
	 * Main method.
	 * @param args stuff
	 */
	public static void main(String[] args) {
		
		createProcesses();
		//printProcesses();


		if (myList[0] != null) { //if the list has at least one process on level 0;

			//This first timer task is what reduces the timeRemaining value of the process every millisecond.
			Timer timer = new Timer();
			TimerTask taskToExecute = new Processor();
			timer.scheduleAtFixedRate(taskToExecute, 0, ONE_MILISEC);

			//This second timer is what prints the state of the program every [TIME_SLICE] milliseconds
			TimerTask taskThatChanges = new Changer();
			timer.scheduleAtFixedRate(taskThatChanges, 0, TIME_SLICE);
			
		}

	}
	

	


	/**
	 * This gets called by Processor's run method at certain intervals.
	 */
	private static void handleProcesses() {
	
		currentProcess.decrementTime();
		
		if (currentProcess.getRemainingTime() == 0) {
			curProcFinish = true;
		}

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
			if (nicerRand < 3) { //most processes will go to priority 2.
				myList[2].enqueue(process);  
			} else { //a few processes will go into the lower levels.
				nicerRand = 3 + rand.nextInt(LEVELS - 3); //priority levels 3 through one number less than (the total levels minus 3).
				myList[nicerRand].enqueue(process); 
			}
		}
	}
	
	
	/**
	 * Prints all the processes in each of the priorities in myList.
	 */
	private static void printProcesses() {
		
		for (int i = 0; i < LEVELS; i++) {
			
			if (myList[i].count > 0) {
				System.out.print("Priority " + i + "\t" + "program #: \t");
				
				System.out.print(myList[i].toString());
				
				/*
				int counter = myList[i].count;
				
				while (counter > 0) {
					Process proc = myList[i].dequeue();
					System.out.print(proc.getNumber() + " ");
					counter --;
					myList[i].enqueue(proc); //put this process back in the list.
				}
				*/
				
				System.out.println();
				if (i != LEVELS - 1) {
					System.out.println("-------------------------------------------------------------------------");
				}
			}
		}
		
		System.out.println();
		System.out.println("=========================================================================================================================================");
		//System.out.println("=========================================================================================================================================");
		System.out.println();
	}
	
	
	/**
	 * This method switches the the process on the processor.
	 */
	private static void switchProcesses() {
		
		printProcesses();
		
		PriorityList plist = myList[currentPrioLvl];
		
		//for loop up until the current level to make sure that no starving processes moved up.
		//if there is a process above the current level, then switch to that level.
		if (currentPrioLvl != 0) {
			for (int i = 0; i < currentPrioLvl; i++) {
				if (myList[i].count > 0) {
					int oldlvl = currentPrioLvl;
					
					currentPrioLvl = i;
					plist = myList[currentPrioLvl]; 
					
					//re-enqueue the current process to its own level if it hasn't finished processing.
					if (currentProcess.getRemainingTime() > 0) {
						myList[oldlvl].enqueue(currentProcess);
					}
				}
			}
		}
		
		//System.out.println("plist count is " + plist.count);
		if (plist.count > 0) { //switch processes if current list has more processes.
			//System.out.println("plist count over 0. count is" + plist.count);
			currentProcess = plist.dequeue();
			curProcFinish = false;
		} else { //the current list is empty so move on to the next priority level.
			
			if (currentPrioLvl != (LEVELS - 1)) { //if we are not on the final level, go to the next level.
				currentPrioLvl ++;
			} else { //we are finished processing
				finished = true;
			}
		}

	}
	
	
	/**
	 * This removes the currently running process because it has been running too long.
	 * That process is added to the back of the queue.
	 */
	private static void removeHogger() {
		//System.out.println("In hogger");
		currentProcess.setRemoveTime();
		myList[currentPrioLvl].enqueue(currentProcess);	
		switchProcesses();
	}
	
	
	
	
	
	
	/**
	 * This class handles the priorities.
	 * @author arshdeep
	 *
	 */
	private static class Processor extends TimerTask {
		

		@Override
		public void run() {
			//System.out.println("running priority: " + currentPrioLvl);
			//System.out.println("curProcFinish is " + curProcFinish);
			if (!finished) { //if we are not done with all the processes.
				
				//System.out.println("Not finished");
				
				if (!curProcFinish) { //if the current process has not finished.
					handleProcesses();
					
					
				} else { //if the current process has finished.
					switchProcesses();
				}
				
			} else { //cancel this run when all finished.
				this.cancel();
			}
		}
	}
	
	
	
	
	/**
	 * This class handles the changing of priorities after they have reached their maximum time on the processor.
	 * @author arshdeep
	 *
	 */
	private static class Changer extends TimerTask {

		@Override
		public void run() {
			
			
			if (!finished) { 
				
				
				//level up any starving processes.
				for (int i = currentPrioLvl; i < LEVELS; i ++) {
					if (i > 0) { //can't level up top level processes.
						Process starver = myList[i].levelUpProcess();
						
						if (starver.getNumber() != -1) { //if this isn't the null node process, add it to the higher level list
							starver.setRemoveTime();
							myList[i - 1].enqueue(starver);
						}
					}
				}
				
				
				if (!curProcFinish) { //switch to next process if current one is not finished.
					//NOTE: if current process is finished, it will be switched out before this Changer class's run is called...
					//...therefore we do not need to do the change again.
					
					removeHogger();
				}
				
				
				
				
			} else {
				
				//printProcesses();
				System.out.println("All Finished!");
				this.cancel();
				
			}
			
		}
	}
	
	
	
	
	
}
