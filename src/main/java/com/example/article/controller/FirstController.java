package com.example.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username","지혜");
        return "greetings";  // hi 입력 받았을 때 그리팅스(머스태치) 반환해준다!
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "지혜");
        return "goodbye";  // hi 입력 받았을 때 그리팅스(머스태치) 반환해준다!
    }
}
