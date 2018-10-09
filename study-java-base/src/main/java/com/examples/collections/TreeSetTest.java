package com.examples.collections;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

public class TreeSetTest {


    public static void main(String[] args) {
        TreeSetTest test = new TreeSetTest();
        test.test();
    }

    public void test(){

        TreeSet<Person> ts = new TreeSet<>();

        boolean success = ts.add(new Person("jason", 20));
        System.out.println("add jason result " + success);
        success = ts.add(new Person("hello", 20));
        System.out.println("add hello result " + success);
//        success = ts.add(null);
//        System.out.println("add null result " + success);

        System.out.println("the size of tree set " + ts.size());


        HashSet<Person> hs = new HashSet<>();

        success = hs.add(new Person("jason", 20));
        System.out.println("add jason to hashset result " + success);
        success = hs.add(new Person("hello", 20));
        System.out.println("add hello to hashset result " + success);
        success = hs.add(null);
        System.out.println("add null to hashset result " + success);

        System.out.println("the size of hash set " + hs.size());
    }


    class Person implements Comparable<Person> {

        private String name;
        private int age;

        public  Person(String theName, int theAge){
            this.name = theName;

            this.age = theAge;
        }

        @Override
        public int compareTo(Person o) {
            return (age == o.age) ? 0  :  (age > o.age ? 1 : -1) ;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

//        @Override
//        public int hashCode() {
//            return Objects.hash(name);
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//
//        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
