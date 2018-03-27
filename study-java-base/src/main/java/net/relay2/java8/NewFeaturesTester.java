package net.relay2.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewFeaturesTester {

    public static void main(String[] args) {
        NewFeaturesTester test = new NewFeaturesTester();
        MathOper add = (int a, int b) -> a + b;
        System.out.println(" 10 + 5 = " + test.operate(10, 5, add));

        List names = new ArrayList();
        names.add("Peter");
        names.add("Linda");
        names.add("Smith");
        names.add("Zack");
        names.add("Bob");

        names.parallelStream().forEach(System.out::println);
        names.stream().forEach(System.out::println);
        Integer value1 = null;
        Optional<Integer> a = Optional.ofNullable(value1);

        Integer b =  a.orElse(0);

        System.out.println("integer is :" + b);


    }

    interface MathOper {
        int operation(int a, int b);
    }

    private int operate(int a, int b, MathOper mathOperation) {
        return mathOperation.operation(a, b);

    }
}
