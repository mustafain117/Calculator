
/*Name: Mustafain Ali Khan
 * ID: 260770776
 */

 /** This class defines the data object used by the Queue and Stack routines.
 */

public class listNode {
	    String data ;
		listNode next;
		listNode previous;
/**
 * This a constructor for the listNode class
 * @param val is used to store strings into the listNode
 */
		public listNode(String val) {
			data= val;
			next= null;
			previous= null;
		}
	}