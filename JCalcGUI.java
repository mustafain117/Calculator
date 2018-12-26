/*Name: Mustafain Ali Khan
 * ID: 260770776
 */


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import acm.gui.TableLayout;
import acm.program.*;

@SuppressWarnings("serial")
public class JCalcGUI extends Program implements ChangeListener, ActionListener{

	public void init() {
	setSize(400,400);	
	
	
	setLayout(new TableLayout(8,4));
	
	//input textfield 
    inputField = new JTextField(10);
    add(inputField, "gridwidth=4");
    
    //text field to display result
     outputField = new JTextField(10);
     outputField.setEditable(false);
     add(outputField, "gridwidth=4");
   
    //buttons that have to be added
    String[] buttonLabels={"C", "(", ")", "+" , "7", "8", "9","-", "4", "5","6","*","1","2","3","/", "0", "."};
    //loop to add buttons 
    for (int i=0 ; i<buttonLabels.length; i++) {
    	add(new JButton(buttonLabels[i]));
    }
    add(new JButton("="), "gridwidth=2");
   
    //label for slider
    label = new JLabel("Precision");
    add(label);
   
    //slider to set precision of answer
    slider = new JSlider();
    slider.setMinimum(1);
    slider.setMaximum(12);
    slider.setValue(6);
    add(slider);
    slider.addChangeListener(this);
   
    //text field to display value of slider
    precision = new JTextField();
    add(precision);
    precision.setText("6");
    
    //adding action listeners so that buttons on calculator can be used
    addActionListeners();
   }
	//To detect slider changes
	@Override
	public void stateChanged(ChangeEvent e) {
     	int prec_slider_value = slider.getValue();//gets value of slider
		precision.setText(prec_slider_value+"");//to display value of slider in text field
    }
	//To detect button presses and decide what action to perfrom
	@Override
	public void actionPerformed(ActionEvent e) {
		String button= e.getActionCommand();
		if (button.equals("C")){//when C button is clicked
			inputField.setText("");
		    outputField.setText("");
		} 
		 else if(button.equals("=")){//when = button is clicked
			 String infix= inputField.getText();
			 String result=JCalc(infix);//the input string is evaluated by calling JCalc
			//displays result after setting precision
			 outputField.setText(setPrecision(result,slider.getValue()));
		}
		 else {
			inputField.setText(inputField.getText()+button);
		}
	}
	
public String JCalc(String infix) {
		 //queue created using Queue class to hold input string(infix form), and outputQueue to hold postfix
	     Queue inputQueue = new Queue(); 
	     Queue outputQueue= new Queue();
	     
	     //using Stack class to create a stack to hold operators
	     Stack Stack = new Stack();
	     
	     //StringTokenizer utility class is used to break infix string into tokens
		 StringTokenizer stringtoken = new StringTokenizer(infix,"+-*/()",true); 
		 
		 //tokens are added into the inputQueue using Enqueue method
		 while (stringtoken.hasMoreTokens()) { 
			 inputQueue.Enqueue(stringtoken.nextToken());
			 
		 }
	    //take tokens from input queue until inputQueue is empty
       while(!inputQueue.isEmpty()) {
	       String token = inputQueue.Dequeue().trim();
	         //if token is an operand(number), put it into the output queue using Enqueue
	          if(isOperand(token)) {
	           outputQueue.Enqueue(token);
	          }
	      
	          //following if condition is true if token is an operator and token is not a bracket
	          if(!isOperand(token) && BracketType (token)==0) {
		    	 	 /*while operator on top of stack has > precedence, 
	    	           pop operators from stack into outputQueue*/
		    	      while( !Stack.isEmpty()  && precedence(Stack.top.data) >= precedence(token)) {
	    	            outputQueue.Enqueue(Stack.pop());
		    		  }
		    		  Stack.push(token); 
		       }
	     
	         //if token is an opening bracket
	         if(BracketType(token)==1) {
	    	     Stack.push(token);
	    	     }
	       
	         //if token is a closing bracket
	         if(BracketType(token)==2) {
             while(BracketType(Stack.top.data)!=1){//pop stack until an opening bracket is at the top of stack
	    	         outputQueue.Enqueue(Stack.pop());
	    	       }
             //pop the opening bracket from top of stack
	    	      Stack.pop();		 
	         }
       }
	    //to pop stack until stack is empty
       while (!Stack.isEmpty()) {
		 outputQueue.Enqueue(Stack.pop());
		}
	  
     //new stack created to evaluate the postfix form
       Stack resultStack= new Stack();
       //loop to dequeue outputQueue until it is empty
       while(!outputQueue.isEmpty()) {
       	 String token = outputQueue.Dequeue();
       	 
       	 //pushing token into stack if token is an operand
       	 if (isOperand(token)) {
       		 resultStack.push(token);
        	}
       	 
       	 /*if the token is an operator, the operation needs to be performed on the last two operands
       	  * therefore, the stack is popped twice and the operation is performed
       	  * the result of the operation is then stored at the top of the stack.
       	  */
       	 if (!isOperand(token)) {
       		 double value2 = Double.parseDouble(resultStack.pop());
       		 double value1 = Double.parseDouble(resultStack.pop());
       		 double result=0;
       		 
       		  if (token.equals("+")){//add value1 and value2
       			  result= value1 + value2;
       			  }
       		  if (token.equals("-")){//subtract value1 and value2
       			  result= value1 - value2;
       			  }
       		  if(token.equals("*")) {//multiply value1 and value2
       			  result= value1 * value2;
       			  }
       		  if(token.equals("/")) {//divide value1 by value2
       			  result= value1 / value2;
       			  }
       		//the value at the top of the stack is our final result
       		 resultStack.push(Double.toString(result));
       		}
       }
      
      String result1= resultStack.pop();
      return result1;
      }

/**
 * This method is used to set the precision of the ouptut according to the slider value set by the user
 * @param result this is the result of the evaluation of psotfix form by JCalc method
 * @param decimals the number of decimal places required in the output
 * @return output value which needs to represented in the calculator
 */
public String setPrecision(String result, int decimals) {
	 
	 double temp= Double.parseDouble(result);
	 double divisor= Math.pow(10, decimals);
	 double answer= Math.round(temp*divisor)/divisor;//Math.round used to round off to required decimal places
	  String resultString= Double.toString(answer);
	  int index= resultString.indexOf('.');//position of decimal place
	  while(resultString.length()-index < decimals+1) {
		  //adding extra 0s to meet precision requirement
			resultString=resultString + "0";
		   }
   	   return resultString;
    }
      
     
/**Checks whether token is an Operand or not. Takes a string as an argument.
 * @return true when token is an operand, false when token is not an operand
 * @param token
 */ 
public boolean isOperand(String token) {
		StringTokenizer st = new StringTokenizer(token, "+-*/()", false);
		return st.hasMoreTokens();
	  }

/**Sets precedence of operators by returning an integer value. Takes a string as an argument.
 * '+' and '-' operators have lower precedence than '*' and '/'
 * @return 1 when token is '+' or '-', 2 when token is '*' and '/' ,
 *  0 when token isn't an operator
 * @param token: a string as a parameter	
 */ 
public int precedence(String token){
	 //on '+' and '-' operators, a smaller integer value is returned as compared to '*' and '/'   
	 if (token.equals("+")  || token.equals("-")) {
			  return 1;
	   }
	 // in the case of '*' and '/', a greater integer value is returned showing greater precedence
	  else if (token.equals("*") || token.equals("/")) {
		      return 2;
	   }
	  else return 0;
 }

/**Used to identify whether bracket is an opening bracket or closing bracket. 
 * Takes a string as an argument.
 * @return 1 when token is an opening bracket, 2 when token is a closing bracket, 0 when none
 * @param token
 */
public int BracketType(String token){
	 if(token.equals("("))
	   return 1;
	 else if(token.equals(")"))
	   return 2;
	 else 
	   return 0;
  }
	
    //private instance variables
	private JTextField inputField;
	private JTextField outputField;
	private JTextField precision;
	private JSlider slider;
	private JLabel label;
}
