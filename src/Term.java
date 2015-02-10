
/* Name: Mihai Vasilcan	
   ID number: 260602604
   Course number: CCCS 315
   Assignment number #1
   Developed in Eclipse 
   */

/* The class Term that has two private data members
 * two constructors, and four methods : multiply, add, getCoeff and getExponent
 * */

public class Term
{ 
	private double coefficient;
	private int exponent;
	
   // Default constructor
   public Term(){
	   
   }
   
   //constructor takes 2 variables
   public Term(double coeff, int exp) 
   { 
      coefficient = coeff; 
      exponent = exp; 
   }
   
   // Method multiplies 2 terms
   public Term multiply(Term polTerm)
   {  
	   double newCoefficient = coefficient * polTerm.coefficient;
	   int newExponent =exponent + polTerm.exponent;
	   return new Term(newCoefficient,newExponent);
      
   }
   
   // method to add a coefficient used when terms have the same exponent
   public void add(double coeff)
   { 
      coefficient += coeff;
   }

   
   public double getCoeff() 
   { 
      return (double)Math.round(coefficient*100.00)/100.00; 
   }
  
   public int getExponent() 
   { 
      return exponent; 
   }
   
   
}
