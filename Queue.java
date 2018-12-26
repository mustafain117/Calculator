
/*Name: Mustafain Ali Khan
 * ID: 260770776
 */

/**
 *This class defines the Queue data structure implemented using a linked list. 
 * A queue is a data structure in which the first input is the first output.
 * This class provides the front and back instance variables.
 * This class also provides the Enqueue() and Dequeue() methods
 */
public class Queue {
	public listNode back;
	public listNode front;
    
	/**Enqueue method adds a string into the queue
	 * 
	 * @param newString the string that is to be added onto the queue
	 */
	public void Enqueue(String newString)
	{
		listNode mynode = new listNode(newString);
		// First we check if the queue is empty
		if(back == null)
		{
			back = mynode;
			front = mynode;
			
		}else
		{
			// If the queue is not empty
			mynode.next = back;
			back.previous = mynode;
			back = mynode;
		}
	}
	/**Dequeue method removes a string from the end of the queue and returns it 
	 * @return the return value is the string from the end of the queue
	 */
	public String Dequeue()
	{
		// This checks whether we a have front node or not
		if(front == null)
		{
			System.out.println("Error: the queue is empty");
			return null;
		}
		
		// Take the result string 
		String result_string = front.data;
		
		if(front.previous == null)
		{			
			front = null;
			back = null;
		}else
		{
			listNode previous_front = front.previous;
			previous_front.next = null;
			front = previous_front;
		}
		
		return result_string;
	}
/** method to print queue to see contents of queue
 */
  public void printQueue(){
	listNode result= front;
	 if (result==null) {
		System.out.println("Empty Queue");
	 }
	 else {
		System.out.print("Postfix: ");
	    while (result!=null) {
	    System.out.println(result.data);
	    result=result.previous;
	    }
	  }
   }
  /** method to check whether the queue is empty or not
   * @return the return value is true if queue is empty and false if it isn't empty
   */
   public boolean isEmpty() {
		return (front==null);
	}
}
