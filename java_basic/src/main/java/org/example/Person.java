package org.example;

public class Person {
    private String name;
    private int age;
    private String sex;

    public void walk(){
        System.out.println("사람이 걷습니다");
    }

    // static 메소드 이므로, 객체를 생성하지 않아도 사용할 수 있다.
    public static void run(){
        System.out.println("사람이 뜁니다.");
    }

    // 직접 생성자 만들기. 매개변수 자유롭게 가능.
    public Person(String name, int age, String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 정적 팩토리 매서드
    // 별도의 객체 생성의 역할을 하는 클래스 메서드
    public static Person create(String name, int age, String sex){
        return new Person(name, age, sex);
    }

}
