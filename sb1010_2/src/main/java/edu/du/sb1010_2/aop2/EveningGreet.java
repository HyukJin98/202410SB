package edu.du.sb1010_2.aop2;


public class EveningGreet implements Greet{

    @Override
    public void greeting() {
        System.out.println("--------------------------------");
        System.out.println("좋은 저녁 입니다.");
        System.out.println("--------------------------------");
    }
}
