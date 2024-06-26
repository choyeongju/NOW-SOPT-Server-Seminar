package org.example;

// 관련이 있는 상수들의 집합
public enum Part {

    SERVER("SERVER"),
    WEB("WEB"),
    ANDROID("ANDROID"),
    DESIGN("DESIGN"),
    PLAN("PLAN"),
    ;
    public String part;

    Part(String part){
        this.part = part;
    }

    public String getPart(){
        return this.part;
    }
}
