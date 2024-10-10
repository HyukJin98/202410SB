package edu.du.sb1010_2.chap07;

public class RecCalculator implements Calculator{
    @Override
    public long factorial(long num) {
//        long start1 = System.nanoTime();
//        long fourFactorial = factorial(4);
//        long end1 = System.nanoTime();
//        System.out.printf("ImplCalculator.factorial(%d) 실행 시간 = %d\n", num, (end1 - start1));
        if(num == 0) return 1;
        else return num * factorial(num - 1);


    }
}
