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
	
	private static int myProcessAmount;
	
	
	
	public static void main(String[] args) {
		
		createProcesses();
		
		
	}
	
	
	public static void createProcesses() {
		Random rand = new Random();
		
		myProcessAmount = rand.nextInt(180) + 15;
		
		
		
		
	}
}
