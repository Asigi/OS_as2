package os_as2p;

import java.util.Date;

public class Process {
	
	
	
	/**
	 * id number of this process
	 */
	private int myNumber;
	
	/**
	 * The creation time in milliseconds (since start of Jan 1, 1970).
	 */
	private long myCreateDate;
	
	
	/**
	 * This represents the time (in milliseconds) for when this process was removed
	 * from the processor.
	 */
	private long myRunFinishTime;
	
	
	
	
	
	/**
	 * Constructor
	 * @param theNum is the id number of the process.
	 */
	public Process(int theNum) {
		
		myNumber = theNum;
		Date adate = new Date();
		myCreateDate = adate.getTime();
		
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
	
	

}
