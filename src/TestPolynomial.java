/* Name: Mihai Vasilcan	
   ID number: 260602604
   Course number: CCCS 315
   Assignment number #1
   Developed in Eclipse 
   */

/*  The main class prompts the user to enter polynomials p(x) and q(x). 
 *  Outputs the two polynomials p(x) and q(x) in increasing order
 *  Outputs the polynomial p(x) * q(x):  p.multiply(q).
 *  Outputs the derivative of p(x):p.derivative(). 
 *  */

import java.util.Scanner;

public class TestPolynomial 
{
	public static void main(String[] args) 
	{
	    Scanner scanner=new Scanner(System.in);
	    
	    while(!quit())
	    {
			System.out.println("\n" + "Please enter polynomial p(x): ");
			String pPolynomial = scanner.nextLine();
			Polynomial.checkPolynomial(pPolynomial);
			
			System.out.println("\n" + "Please enter polynomial q(x): ");
			String qPolynomial = scanner.nextLine();
			Polynomial.checkPolynomial(qPolynomial);
					
		    Polynomial p = Polynomial.getFromString(Polynomial.polyTerm(pPolynomial));
			Polynomial q = Polynomial.getFromString(Polynomial.polyTerm(qPolynomial));
			
		    System.out.println("\n" + "p(x) = " + p);
			System.out.println("q(x) = " + q);
			
			System.out.println("p(x)* q(x) = " + p.multiply(q));
			System.out.println("The derivative of p(x) is p'(x)= " + p.derivative(p));
			
	   }
	 }
		    // Method to do a loop while inputing polynomials
		    public static boolean quit()
		    {
				Scanner scanner=new Scanner(System.in);
				boolean quits = false;
				System.out.print("\n" + "Enter your choice: q to quit or any other letter to continue ");
				String answer = scanner.nextLine();
			    if(answer.equals("q"))
			    {
			    	quits= true;
			    }
			    return quits;
	        }
		   
}
