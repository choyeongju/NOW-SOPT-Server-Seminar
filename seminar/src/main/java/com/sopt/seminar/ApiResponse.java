package com.sopt.seminar;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 클래스 활용하여 JSON 객체 만들기
@AllArgsConstructor
@Getter //response 객체의 필드에 접근해야 하므로, 이 어노테이션 없으면 통신 안된다.
public class ApiResponse {
    String content;

    public static ApiResponse create(String content){
        return new ApiResponse(content);
    }
}
