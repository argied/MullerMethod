package org.mariuszgromada.math.mxparser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Muller {
	public static void main (String [] args) {		
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nInput f(x): ");
		String function = scanner.nextLine();
		System.out.print("Input x0: ");
		String xValue0 = scanner.nextLine();
		System.out.print("Input x1: ");
		String xValue1 = scanner.nextLine();
		System.out.print("Input x2: ");
		String xValue2 = scanner.nextLine();
		System.out.print("Input Ea: ");
		double ea = scanner.nextDouble();

		muller (function,xValue0,xValue1,xValue2, ea);			

		scanner.close();
	}

	static double rnd(double answer, int DCplace) {
		BigDecimal decimal = new BigDecimal(Double.toString(answer));
		decimal = decimal.setScale(DCplace, RoundingMode.HALF_UP);
		return decimal.doubleValue();
	}

	static String muller(String function, String xValue0, String xValue1, String xValue2, double ea) {
		DecimalFormat format = new DecimalFormat("#.####"); 
		format.setRoundingMode(RoundingMode.HALF_UP);

		Function f = new Function ("f(x) = "+function); 
		Argument x0 = new Argument ("x = "+xValue0);
		Argument x1 = new Argument ("x = "+xValue1);
		Argument x2 = new Argument ("x = "+xValue2);

		Expression f0 = new Expression ("f(x)", f, x0);
		Expression f1 = new Expression ("f(x)", f, x1);
		Expression f2 = new Expression ("f(x)", f, x2);			

		System.out.print("\n---------------------------------------------------------------------------------------------");
		System.out.printf("\n%s%11s%10s%10s%10s%10s%10s%10s%10s", "Iteration", "x0", "x1", "x2", "f(x0)", "f(x1)", "f(x2)", "h0","h1");
		System.out.print("\n---------------------------------------------------------------------------------------------");

		double xx0 = Double.valueOf(xValue0);
		double xx1 = Double.valueOf(xValue1);
		double xx2 = Double.valueOf(xValue2);

		double h0 = xx1-xx0;
		double h1 = xx2-xx1;
		double d0 = (rnd (f1.calculate(),4)-rnd (f0.calculate(),4))/rnd(h0,4);
		double d1 = (rnd (f2.calculate(),4)-rnd (f1.calculate(),4))/rnd(h1,4);

		double a = (rnd(d1,4)-rnd(d0,4))/(rnd(h1,4)+rnd(h0,4));
		double b = (rnd(a,4)*rnd(h1,4)) +rnd(d1,4);
		double c = rnd (f2.calculate(),4);
		double d = Math.sqrt(rnd (Math.pow(b, 2),4)-(4*rnd(a,4)*rnd(c,4)));
		double bd =0;
		if (b>=0)bd = rnd(b,4)+rnd(d,4);
		if (b<=0)bd = rnd(b,4)-rnd(d,4);
		double xx3 = xx2+((-2*rnd(c,4))/(rnd(bd,4)));
		double Ea = Math.abs((rnd(xx3,4) -rnd(xx2,4) )/rnd(xx3,4))*100;
		System.out.printf("\n%5d%15s%10s%10s%10s%10s%10s%10s%10s\n", 1, format.format(xx0), format.format(xx1), format.format(xx2), format.format(f0.calculate()), format.format(f1.calculate()), format.format(f2.calculate()), format.format(h0),format.format(h1));
		for (int i = 2; Ea>ea;i++) {
			String newX0 = String.valueOf(rnd(xx1, 4));
			String newX1 = String.valueOf(rnd(xx2, 4));
			String newX2 = String.valueOf(rnd(xx3, 4));

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
			d0 = (rnd (f1.calculate(),4)-rnd (f0.calculate(),4))/rnd(h0,4);
			d1 = (rnd (f2.calculate(),4)-rnd (f1.calculate(),4))/rnd(h1,4);

			a = (rnd(d1,4)-rnd(d0,4))/(rnd(h1,4)+rnd(h0,4));
			b = (rnd(a,4)*rnd(h1,4)) +rnd(d1,4);
			c = rnd (f2.calculate(),4);
			d = Math.sqrt(rnd (Math.pow(b, 2),4)-(4*rnd(a,4)*rnd(c,4)));

			if (b>=0)bd = rnd(b,4)+rnd(d,4);
			if (b<=0)bd = rnd(b,4)-rnd(d,4);

			xx3 = rnd(xx2,4)+((-2*rnd(c,4))/(rnd(bd,4)));
			Ea = Math.abs((rnd(xx3,4) -rnd(xx2,4) )/rnd(xx3,4))*100;

			System.out.printf("%5d%15s%10s%10s%10s%10s%10s%10s%10s\n", i, format.format(xx0), format.format(xx1), format.format(xx2), format.format(f0.calculate()), format.format(f1.calculate()), format.format(f2.calculate()), format.format(h0),format.format(h1));
			if (i>100) break;
			if (Ea == 0)break;
		}
		System.out.print("---------------------------------------------------------------------------------------------");


		//for 2nd table
		f0 = new Expression ("f(x)", f, x0);
		f1 = new Expression ("f(x)", f, x1);
		f2 = new Expression ("f(x)", f, x2);	
		System.out.print("\n\n---------------------------------------------------------------------------------------------");
		System.out.printf("\n%s%11s%10s%10s%10s%10s%10s%10s%10s", "Iteration","d0", "d1", "a", "b", "c", "d", "x3", "Ea");
		System.out.print("\n---------------------------------------------------------------------------------------------");

		xx0 = Double.valueOf(xValue0);
		xx1 = Double.valueOf(xValue1);
		xx2 = Double.valueOf(xValue2);

		h0 = xx1-xx0;
		h1 = xx2-xx1;
		d0 = (rnd (f1.calculate(),4)-rnd (f0.calculate(),4))/rnd(h0,4);
		d1 = (rnd (f2.calculate(),4)-rnd (f1.calculate(),4))/rnd(h1,4);

		a = (rnd(d1,4)-rnd(d0,4))/(rnd(h1,4)+rnd(h0,4));
		b = (rnd(a,4)*rnd(h1,4)) +rnd(d1,4);
		c = rnd (f2.calculate(),4);
		d = Math.sqrt(rnd (Math.pow(b, 2),4)-(4*rnd(a,4)*rnd(c,4)));

		if (b>=0)bd = rnd(b,4)+rnd(d,4);
		if (b<=0)bd = rnd(b,4)-rnd(d,4);
		xx3 = xx2+((-2*rnd(c,4))/(rnd(bd,4)));
		Ea = Math.abs((rnd(xx3,4) -rnd(xx2,4) )/rnd(xx3,4))*100;
		System.out.printf("\n%5d%15s%10s%10s%10s%10s%10s%10s%10s\n", 1, format.format(d0), format.format(d1), format.format(a), format.format(b), format.format(c), format.format(d), format.format(xx3), format.format(Ea));          
		for (int i = 2; Ea>ea;i++) {
			String newX0 = String.valueOf(rnd(xx1, 4));
			String newX1 = String.valueOf(rnd(xx2, 4));
			String newX2 = String.valueOf(rnd(xx3, 4));

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
			d0 = (rnd (f1.calculate(),4)-rnd (f0.calculate(),4))/rnd(h0,4);
			d1 = (rnd (f2.calculate(),4)-rnd (f1.calculate(),4))/rnd(h1,4);

			a = (rnd(d1,4)-rnd(d0,4))/(rnd(h1,4)+rnd(h0,4));
			b = (rnd(a,4)*rnd(h1,4)) +rnd(d1,4);
			c = rnd (f2.calculate(),4);
			d = Math.sqrt(rnd (Math.pow(b, 2),4)-(4*rnd(a,4)*rnd(c,4)));

			if (b>=0)bd = rnd(b,4)+rnd(d,4);
			if (b<=0)bd = rnd(b,4)-rnd(d,4);

			xx3 = rnd(xx2,4)+((-2*rnd(c,4))/(rnd(bd,4)));
			Ea = Math.abs((rnd(xx3,4) -rnd(xx2,4) )/rnd(xx3,4))*100;

			System.out.printf("%5d%15s%10s%10s%10s%10s%10s%10s%10s\n", i, format.format(d0), format.format(d1), format.format(a), format.format(b), format.format(c), format.format(d), format.format(xx3), format.format(Ea));          
			if (i>100) break;
			if (Ea == 0)break;
		}
		System.out.print("---------------------------------------------------------------------------------------------");
		System.out.print("\nROOT: "+ format.format(xx3));

		return (format.format(xx3));
	}
}