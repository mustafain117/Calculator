/*Name: Mustafain Ali Khan
 * ID: 260770776
*/
/**This class implements a stack which is a data structure used to store strings in this case.
 * Stacks use the first in last out method, which means the new input(string) is added to the top 
 * of the stack. This class also provides the pop() and push() methods along with the instance variable top.
 */

public class Stack {
public listNode top;
	
	/**The push() method is used to add a new string to the top of the stack. 
	 * @param newString is any string that needs to be added to the top of the stack.
	 */
	public void push(String newString)
	{
		listNode mynode = new listNode(newString);
		
		if(top != null)
		{		
			top.next = mynode;
			mynode.previous = top;		
		}
		top = mynode;
		 
	}
	
	/**The pop() method is used to remove the string on top of the stack and return it. 
	 * The string below the top becomes the new top.
	 * @return the return value is the string at the top of the stack.
	 */
	public String pop()
	{
		if(top == null)
		{
			System.out.println("Error: empty stack");
			return null;
		}
		
		String result_string = top.data;		
		// we have one node in the stack
		if(top.previous == null)
		{
			top = null;  
		}else
		{
			// we have more than one node in the stack
			listNode pre_top = top.previous;
			pre_top.next = null;
			top = pre_top;
			
		}			
		return result_string;
	}		
	/**This method is used to check whether the stack is empty or not.
	 * @return the return value is true if stack is empty,i.e top = null.
	 * the return value is false if there is at least one string in the stack.
	 */
	public boolean isEmpty() {
		return(top==null);
	}
	}
