


//Arshdeep Singh
//Roman 

//Assignment 2 Processes





package os_as2p;

import java.util.Date;

public class Process {
	
	
	
	/**
	 * id number of this process
	 */
	private int myNumber;
	
	
	/**
	 * How much more time this process will need on the processor to finish.
	 */
	private int myRemainingTime;
	
	
	/**
	 * The creation time in milliseconds (since start of Jan 1, 1970).
	 */
	private long myCreateDate;
	
	
	/**
	 * This represents the time (in milliseconds) for when this process was removed
	 * from the processor. This field is used to figure out whether this process 
	 * is starving or not.
	 * 
	 * This value is initialized to the time it is created.
	 */
	private long myRunFinishTime;
	
	
	
	
	
	/**
	 * Constructor
	 * @param theNum is the id number of the process.
	 */
	public Process(int theNum, int runTime) {
		
		myNumber = theNum;
		myRemainingTime = runTime;
		
		Date adate = new Date();
		myCreateDate = adate.getTime();
		
		myRunFinishTime = myCreateDate; 
	}
	
	
	/**
	 * Return the number.
	 * @return the id number of this process.
	 */
	public int getNumber() {
		
		return myNumber;
	}
	
	
	/**
	 * This sets the time for when this process was removed from the processor.
	 * OR 
	 * This could be called for resetting the time for when this process was moved up a level.
	 */
	public void setRemoveTime() {
		
		Date adate = new Date();
		myRunFinishTime = adate.getTime();
	}
	
	
	
	/**
	 * This method is called to see if this process has been starving for too
	 * long. The check for starvation will happen in the caller class.
	 * 
	 * @param theDate the date object passed in is from the thing that 
	 * controls what process gets to run.
	 * 
	 */
	public int getStarveTime(Date theDate) {
		
		return (int) (theDate.getTime() - myRunFinishTime);
	}
	
	
	/**
	 * Get the time in milliseconds.
	 * @return the amount of time more this process needs to finish.
	 */
	public int getRemainingTime() {
		return myRemainingTime;
	}
	
	/**
	 * Decrement the remaining time by one millisecond.
	 */
	public void decrementTime() {
		myRemainingTime --;
	}

}
