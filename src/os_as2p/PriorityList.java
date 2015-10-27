package os_as2p;

import java.util.Date;

public class PriorityList {

	/**
	 * A period of time (in milliseconds) that determines when a node is considered to be starving.
	 */
	public static final int STARVE_TIME = 10000; //10 seconds
	
	/**
	 * Points to the first node.
	 */
	private ProcessNode front;
	
	/**
	 * Points to the last node.
	 */
	private ProcessNode back;
	
	/**
	 * number of items in the list.
	 */
	private int count;
	
	
	
	
	
	public PriorityList() {
		front = null;
		back = null;
		count = 0;
	}
	
	
	
	/**
	 * Adds a process to the back of the queue
	 * @param theProc the Process to add.
	 */
	public void enqueue(Process theProc) {
		ProcessNode node = new ProcessNode(theProc);
		
		if (front == null) {
			front = node;
			back = node;
			count++;
			
		} else {
			back.next = node;
			back = node;
			count++;
		}
	}
	
	/**
	 * This removes the first process (node) in the list.
	 * @return the removed process.
	 */
	public Process dequeue() {
		ProcessNode ret = front;
		
		front = front.next;
		count--;
		
		return ret.data;
	}
	
	
	/**
	 * This method is used to find the node that contains a starving process.
	 * @return the node that contains the starving process.
	 */
	public ProcessNode levelUpProcess() {
	
		ProcessNode previous = null;
		ProcessNode current = null;
		
		if (front != null) {
			current = front;
			
			if (checkForStarvation(current)) {
				front = front.next; //remove the current node that is starving. TODO: what if node is at priority 0?
				count--;
				return current;
				
			} else {
				while (current.next != null) {
					previous = current;
					current = current.next;
					
					if (checkForStarvation(current)) {
						previous.next = current.next; //remove the current
						count--;
						return current;
					}
				}
			}
		} 
		
		return current; //null
	}
	
	
	
	
	/**
	 * Check if the passed in node contains a starving process.
	 * @param theNode is the node to check
	 * @return true if starving, false otherwise.
	 */
	private boolean checkForStarvation(ProcessNode theNode) {

		if (theNode != null) {
			
			Date adate = new Date();
			
			if (theNode.data.getStarveTime(adate) > STARVE_TIME) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	
	/**
	 * This is an inner class for the purposes of creating a node.
	 * @author arshdeep
	 *
	 */
	protected class ProcessNode {
		
		private Process data;
		private ProcessNode next;
		
		public ProcessNode(Process theProcess) {
			
			data = theProcess;
		}
		
		public void setNext(ProcessNode theNext) {
			
			next = theNext;
		}
		
		public ProcessNode getNext() {
			return next;
		}
		
		public Process getProcess() {
			
			return data;
		}
		
	}
}
