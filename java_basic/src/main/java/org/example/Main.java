package org.example;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("조영주", 23,"female");
        person.walk();

        System.out.println(person.getName());
        person.setName("앵주");
        System.out.println(person.getName());

        // 빌더 패턴으로 객체 생성해보기
        Person personWithBuilder = new PersonBuilder()
                .name("조성흠")
                .age(21)
                .sex("female")
                .build();

        System.out.println(personWithBuilder.getName());

        // 정적 패토리 메서드를 활용해 간접적으로 객체 생성
        Person personWithFactoryMethod = Person.create("조영주", 23, "female");
    }
}