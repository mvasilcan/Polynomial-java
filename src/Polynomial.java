
   
/* The following Polynomial class implements the polynomial operations
 * It has 2 constructors a default and a copy constructor
 * It has the following methods that perform polynomial operations:
 *  addTerm, add, multiply, derivative and toString
 *  The methods cleanPolynomial, polyTerm, getFromString, containsIllegals,
 *  containOnlyX and checkPolynomial deal with transforming a string into a polynomial */

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
	

public class Polynomial
{ 
   private LinkedList polynomialTerms;
   
   //Default constructor
   public Polynomial()
   { 
      polynomialTerms = new LinkedList();
   }
   
   //copy constructor
   public Polynomial(Polynomial q)  
   { 
	   polynomialTerms = new LinkedList();
	   ListIterator it = q.polynomialTerms.listIterator();
	   while (it.hasNext())
	   {
	      addTerm((Term)it.next());
	   }   
   }
   
   // add a term to this polynomial. The term has two parameters a double and an int.
   public void addTerm(double coeff, int exp)
   { 
      addTerm(new Term(coeff, exp));
   }
   
   // add a term to this polynomial. If this polynomial already has a term
   // with the same exponent as the argument, then the argument term will be
   // combined with the existing one. 
   public void addTerm(Term t)
   { 
      double coefficient = t.getCoeff();
      int exponent = t.getExponent();

      ListIterator it = polynomialTerms.listIterator();
      while (it.hasNext())
      { 
         Term m = (Term)it.next();
         if (exponent == m.getExponent())
         { 
            if (coefficient == - m.getCoeff())
               it.remove();
            else
               m.add(coefficient);
            return;
         }
         else if (exponent< m.getExponent())
         { 
            it.previous();
            it.add(t);
            return;
         }
      }
      it.add(t);
   }

   // method to add two polynomials 
   public Polynomial add(Polynomial q)
   {  
      Polynomial result = new Polynomial();
      ListIterator it = polynomialTerms.listIterator();
      
      while (it.hasNext())
      { 
         result.addTerm((Term)it.next());
      }
      ListIterator qIterator = q.polynomialTerms.listIterator();
      
      while (qIterator.hasNext())
      { 
         result.addTerm((Term)qIterator.next());
      }
      return result;
   }
   
   
   //Method to multiply two polynomials
   //Postcondition: This polynomial is multiplied with the
   //polynomial specified by the parameter q. 
   //A reference of the result is returned.

   public Polynomial multiply(Polynomial q)
   { 
      Polynomial result = new Polynomial();
      ListIterator it = polynomialTerms.listIterator();
      
      while (it.hasNext())
      { 
         ListIterator qIt = q.polynomialTerms.listIterator();
         Term m = (Term)it.next();
         
         while (qIt.hasNext())
         {  
            Term n = (Term)qIt.next();
            result.addTerm(m.multiply(n));  
         }
      }
      return result;
   }
   //Method to return the derivative
   public Polynomial derivative(Polynomial p)
   {
      Polynomial result = new Polynomial();
      ListIterator iterator = polynomialTerms.listIterator();
      
      while (iterator.hasNext())
      {
    	  ListIterator it = p.polynomialTerms.listIterator();
          Term t = (Term)iterator.next();
          result.addTerm(new Term( t.getCoeff() * t.getExponent(), t.getExponent() -1 ));   
       }
       return result;        
    }
  
   //Method to return the string containing the polynomial 
   public String toString()
   {
	   // returns the string only with 2 decimals
	   DecimalFormat df = new DecimalFormat("#.00");
	   Term nextT;
	   ListIterator iterator = polynomialTerms.listIterator();
	   String result = "";
	   boolean stop = false;
       
	   while (iterator.hasNext()) 
	   {
		   nextT = (Term)iterator.next();
		   		
	       		if ((nextT.getCoeff() > 0) && stop)
	       			result += "+ ";
	       			stop = true;
	       		
	    	    if (nextT.getCoeff() == 0)
	    	    	result += "";   
	    	    else if (nextT.getExponent() == 1)
	    	    {
	    	    	if(nextT.getCoeff()==1)
	    	    		result +=  "x" + " ";
	    	    	else
	    	    		result += nextT.getCoeff() + "x" + " ";
	    	    }
	    	    else if(nextT.getExponent() ==0)
	    	    	result += nextT.getCoeff() + " ";  
	    	    else if (nextT.getCoeff() == 1)
	    	    	result += "x^" + nextT.getExponent() + " ";
	    	    else if (nextT.getCoeff() == -1)
	    	    	result += "-x^" + nextT.getExponent() + " ";
	    	    else if (nextT.getExponent() == 0)
	    	    	result += nextT.getCoeff()+ " ";
	    	    else
	    	    	result += nextT.getCoeff() + "x^" + nextT.getExponent() + " ";
	   }
       		if (result == "" || result.length()==0)
       			result = "0";
    
       		return result;}
    
   
   //Method to clean the inputed string 
   
   public static String cleanPolynomial(String s)
	{
		if(s.substring(s.length()-1).matches("x"))
		{   
		   s =  s.substring(0,s.length()-1)+s.substring(s.length()-1) + "^1";;	   
		} 		
		 s = s.replaceAll("\\ ", "");
		 //s = s.replaceAll("\\++", "+");//maybe delete
		 s = s.replaceAll("\\--", "++");
		 s = s.replaceAll("\\-", "+-");
		 s = s.replaceAll("\\Q-+\\E", "+-");
		 s = s.replaceAll("\\Qx+\\E", "x^1+");
		 s = s.replaceAll("\\Qx-\\E", "x^1-");
		 s = s.replaceAll("\\Q-x\\E", "+-1x");
		 //s = s.replaceAll("\\+-", "-");
		 //System.out.println(s);
		
	     return s;	     
		}
	
   //Method calls cleanPolynomial and uses the cleaned string to get 
   // an array term(coefficient,exponent)
   // the array is used by the method getFromstring to get the values
	public static String[] polyTerm (String s)
	{ 
		s = cleanPolynomial(s);
		String regex = "\\Q+\\E";
		String [] input = s.split((regex));
		String []term = new String[input.length];
		 
		for (int i=0; i<input.length;i++)
		{
			// replaces x^ with a comma
		   input[i]= input[i].replaceAll("\\Qx^\\E", ",");
		   if( input[i].contains(","))
			  input[i]= input[i].replaceAll("\\Qx^\\E", ",");
		   else
			  input[i]=input[i].substring(0,input[i].length())+ (",0");
		   }
		 // takes care of a bug.The first term returns as(,0) and adds a 0 to it 
		 for (int i=0; i<input.length;i++)
		 {
		    if( input[i].equals(",0"))
			   input[i]=("0")+input[i].substring(0,input[i].length());
		 }
		 // takes care of another bug after split
		 for (int i=0; i<input.length;i++)
		 {
		     if( input[i].equals(",1"))
			 input[i]=("1")+input[i].substring(0,input[i].length());
		 }
		 for (int i=0; i<input.length;i++)
		 { 
			term[i]=input[i];
		 }
		  return term;
		}
	// Method to extract the polynomial from a string of terms (coefficient, exponent)
	   public static Polynomial getFromString (String [] userInput) 
	   {
	       double coefficient = 0;
	       int exponent = 0 ;
		   Polynomial inputedPolynomial = new Polynomial();
	       String[] term;
	      
	       for (int i = 0; i < userInput.length; i++) 
	       {
	    	   // split the string on comma.
	           term = userInput[i].substring(0, userInput[i].length()).split("\\,");
	           
	           // for some particular cases when I copy paste the polynomial from a word file i get an error
	           // Inputing the polynomial from the keyboard doesn't result in an error.
	           try{
	           // parse the first term of the string and return 2 decimals 
	           coefficient = (double)Math.round(Double.parseDouble(term[0])*100.00) / 100.00;
	           
	           // parse the second term of the string
	           
	           exponent = Integer.parseInt(term[1]); 
	           }
	           catch(NumberFormatException e){
	        	   System.out.println("If you used copy paste please enter the polynomial using the keyboard. ");
	        	   System.out.println("Please restart the program.");
	        	   System.exit(1);
	           }
	           
	           Term t = new Term(coefficient, exponent);
	           //Add the new term.
	           
	           inputedPolynomial.addTerm(t);
	        }
	       
	      return inputedPolynomial;
		      }
	   
	   
	   
	// Method returns true if any illegal characters are inputed in the string
	public static boolean containsIllegals(String input) 
	{
	    Pattern pattern = Pattern.compile("[~#@*&!$()%{}<>\\[\\]|\"\\_]");
	    Matcher matcher = pattern.matcher(input);
	    return matcher.find();
	}
	
	//Method returns true if characters other than x are inputed
	public static boolean containsOnlyX(String input) 
	{
		input = input.replaceAll("x", "");
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
	    Matcher matcher = pattern.matcher(input);
	    return matcher.find();
    }
	
	//Method display error message if illegal characters are inputed by user
	
	public static void checkPolynomial(String p)
	{
		if(Polynomial.containsIllegals(p))
		{
			System.out.println("Please enter a valid polynomial.");
			System.out.println("You entered an invalid character");
		}
	
		if(Polynomial.containsOnlyX(p))
		{
			System.out.println("Please enter a valid polynomial.");
			System.out.println("The only accepted form for a variable is x");
		}
	}
	
}
	
	
 


