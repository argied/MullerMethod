package org.mariuszgromada.math.mxparser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Muller {
	
		static double round(double value, int places) {
			if (places < 0) throw new IllegalArgumentException();
			BigDecimal bd = new BigDecimal(Double.toString(value));
			bd = bd.setScale(places, RoundingMode.HALF_UP);
			return bd.doubleValue();
			}
	
		public static void main (String [] args) {		
			Scanner input = new Scanner(System.in);
			DecimalFormat df = new DecimalFormat("#.####"); 
			df.setRoundingMode(RoundingMode.HALF_UP);
			        
			System.out.print("Enter function: ");
			String function = input.nextLine();
			System.out.print("Enter value of x0: ");
			String xValue0 = input.nextLine();
			System.out.print("Enter value of x1: ");
			String xValue1 = input.nextLine();
			System.out.print("Enter value of x2: ");
			String xValue2 = input.nextLine();
			System.out.print("Enter value of Ea: ");
			double ea = input.nextDouble();

			Function f = new Function ("f(x) = "+function); 
			Argument x0 = new Argument ("x = "+xValue0);
			Argument x1 = new Argument ("x = "+xValue1);
			Argument x2 = new Argument ("x = "+xValue2);

			Expression f0 = new Expression ("f(x)", f, x0);
			Expression f1 = new Expression ("f(x)", f, x1);
			Expression f2 = new Expression ("f(x)", f, x2);			

			System.out.print("\n----------------------------------------------------------------------------------------");
			System.out.printf("\n%s%7s%9s%10s%10s%10s%10s%10s%10s", "Iteration", "x0", "x1", "x2", "f(x0)", "f(x1)", "f(x2)", "h0","h1");
			System.out.print("\n----------------------------------------------------------------------------------------");

			double xx0 = Double.valueOf(xValue0);
			double xx1 = Double.valueOf(xValue1);
			double xx2 = Double.valueOf(xValue2);

			double h0 = xx1-xx0;
			double h1 = xx2-xx1;
			double d0 = (round (f1.calculate(),4)-round (f0.calculate(),4))/round(h0,4);
			double d1 = (round (f2.calculate(),4)-round (f1.calculate(),4))/round(h1,4);
			
			double a = (round(d1,4)-round(d0,4))/(round(h1,4)+round(h0,4));
			double b = (round(a,4)*round(h1,4)) +round(d1,4);
			double c = round (f2.calculate(),4);
			double d = Math.sqrt(round (Math.pow(b, 2),4)-(4*round(a,4)*round(c,4)));
			
			double bd=0;
			if (b>=0)bd = round(b,4)+round(d,4);
			if (b<=0)bd = round(b,4)-round(d,4);
			double xx3 = xx2+((-2*round(c,4))/(round(bd,4)));
			double Ea = Math.abs((round(xx3,4) -round(xx2,4) )/round(xx3,4))*100;
			System.out.printf("\n%5d%10s%10s%10s%10s%10s%10s%10s%10s\n", 1, df.format(xx0), df.format(xx1), df.format(xx2), df.format(f0.calculate()), df.format(f1.calculate()), df.format(f2.calculate()), df.format(h0),df.format(h1));
			int i =2;
			while (Ea>ea) {
				String newX0 = String.valueOf(round(xx1, 4));
				String newX1 = String.valueOf(round(xx2, 4));
				String newX2 = String.valueOf(round(xx3, 4));

				Argument test0= new Argument ("x = "+newX0);
				Argument test1= new Argument ("x = "+newX1);
				Argument test2= new Argument ("x = "+newX2);
				
				f0 = new Expression ("f(x)", f, test0);
				f1 = new Expression ("f(x)", f, test1);
				f2 = new Expression ("f(x)", f, test2);
				
				xx0=xx1;
				xx1=xx2;
				xx2 = xx3;	
				
				h0 = xx1-xx0;
				h1 = xx2-xx1;
				d0 = (round (f1.calculate(),4)-round (f0.calculate(),4))/round(h0,4);
				d1 = (round (f2.calculate(),4)-round (f1.calculate(),4))/round(h1,4);
				
				a = (round(d1,4)-round(d0,4))/(round(h1,4)+round(h0,4));
				b = (round(a,4)*round(h1,4)) +round(d1,4);
				c = round (f2.calculate(),4);
				d = Math.sqrt(round (Math.pow(b, 2),4)-(4*round(a,4)*round(c,4)));
				
				if (b>=0)bd = round(b,4)+round(d,4);
				if (b<=0)bd = round(b,4)-round(d,4);
				
				xx3 = round(xx2,4)+((-2*round(c,4))/(round(bd,4)));				
				Ea = Math.abs((round(xx3,4) -round(xx2,4) )/round(xx3,4))*100;

				System.out.printf("%5d%10s%10s%10s%10s%10s%10s%10s%10s\n", i, df.format(xx0), df.format(xx1), df.format(xx2), df.format(f0.calculate()), df.format(f1.calculate()), df.format(f2.calculate()), df.format(h0),df.format(h1));
				i++;            
				if (i>100) break;
				if (Ea == 0)break;
			}
			System.out.print("----------------------------------------------------------------------------------------");
			
			
			
			//for 2nd table
			f = new Function ("f(x) = "+function); 
			x0 = new Argument ("x = "+xValue0);
			x1 = new Argument ("x = "+xValue1);
			x2 = new Argument ("x = "+xValue2);

			f0 = new Expression ("f(x)", f, x0);
			f1 = new Expression ("f(x)", f, x1);
			f2 = new Expression ("f(x)", f, x2);			

			System.out.print("\n\n----------------------------------------------------------------------------------------");
			System.out.printf("\n%s%7s%9s%10s%10s%10s%10s%10s%10s", "Iteration","d0", "d1", "a", "b", "c", "d", "x3", "Ea");
			System.out.print("\n----------------------------------------------------------------------------------------");

			xx0 = Double.valueOf(xValue0);
			xx1 = Double.valueOf(xValue1);
			xx2 = Double.valueOf(xValue2);

			h0 = xx1-xx0;
			h1 = xx2-xx1;
			d0 = (round (f1.calculate(),4)-round (f0.calculate(),4))/round(h0,4);
			d1 = (round (f2.calculate(),4)-round (f1.calculate(),4))/round(h1,4);
			
			a = (round(d1,4)-round(d0,4))/(round(h1,4)+round(h0,4));
			b = (round(a,4)*round(h1,4)) +round(d1,4);
			c = round (f2.calculate(),4);
			d = Math.sqrt(round (Math.pow(b, 2),4)-(4*round(a,4)*round(c,4)));
			
			if (b>=0)bd = round(b,4)+round(d,4);
			if (b<=0)bd = round(b,4)-round(d,4);
			xx3 = xx2+((-2*round(c,4))/(round(bd,4)));
			Ea = Math.abs((round(xx3,4) -round(xx2,4) )/round(xx3,4))*100;
			System.out.printf("\n%5d%10s%10s%10s%10s%10s%10s%10s%10s\n", 1, df.format(d0), df.format(d1), df.format(a), df.format(b), df.format(c), df.format(d), df.format(xx3), df.format(Ea));
			i = 2;
			while (Ea>ea) {
				String newX0 = String.valueOf(round(xx1, 4));
				String newX1 = String.valueOf(round(xx2, 4));
				String newX2 = String.valueOf(round(xx3, 4));

				Argument test0= new Argument ("x = "+newX0);
				Argument test1= new Argument ("x = "+newX1);
				Argument test2= new Argument ("x = "+newX2);
				
				f0 = new Expression ("f(x)", f, test0);
				f1 = new Expression ("f(x)", f, test1);
				f2 = new Expression ("f(x)", f, test2);
				
				xx0=xx1;
				xx1=xx2;
				xx2 = xx3;		
				
				h0 = xx1-xx0;
				h1 = xx2-xx1;
				d0 = (round (f1.calculate(),4)-round (f0.calculate(),4))/round(h0,4);
				d1 = (round (f2.calculate(),4)-round (f1.calculate(),4))/round(h1,4);
				
				a = (round(d1,4)-round(d0,4))/(round(h1,4)+round(h0,4));
				b = (round(a,4)*round(h1,4)) +round(d1,4);
				c = round (f2.calculate(),4);
				d = Math.sqrt(round (Math.pow(b, 2),4)-(4*round(a,4)*round(c,4)));
				
				if (b>=0)bd = round(b,4)+round(d,4);
				if (b<=0)bd = round(b,4)-round(d,4);
				
				xx3 = round(xx2,4)+((-2*round(c,4))/(round(bd,4)));
				Ea = Math.abs((round(xx3,4) -round(xx2,4) )/round(xx3,4))*100;

				System.out.printf("%5d%10s%10s%10s%10s%10s%10s%10s%10s\n", i, df.format(d0), df.format(d1), df.format(a), df.format(b), df.format(c), df.format(d), df.format(xx3), df.format(Ea));
				i++;            
				if (i>100) break;
				if (Ea == 0)break;
			}
			System.out.print("----------------------------------------------------------------------------------------");
			System.out.print("\nThe root is approximately "+df.format(xx3));
						
			input.close();
			
	}
}