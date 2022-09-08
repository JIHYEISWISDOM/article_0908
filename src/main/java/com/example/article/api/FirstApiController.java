package com.example.article.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //RestAPI용 컨트롤러! JSON을 반환하는 거 ! 제이슨이나 텍스트를 반환함 <-> 일반 컨트롤러는 페이지를 반환!
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello world!";
    }
}
