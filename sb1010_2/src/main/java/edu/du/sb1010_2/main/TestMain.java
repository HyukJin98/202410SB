package edu.du.sb1010_2.main;

import edu.du.sb1010_2.chap07.Calculator;
import edu.du.sb1010_2.chap07.ImplCalculator;
import edu.du.sb1010_2.chap07.RecCalculator;

public class TestMain {
    public static void main(String[] args) {
        Calculator calculator = new ImplCalculator();
        Calculator calculator2 = new RecCalculator();
        System.out.println(calculator.factorial(5));
        System.out.println(calculator2.factorial(5));
    }
}
