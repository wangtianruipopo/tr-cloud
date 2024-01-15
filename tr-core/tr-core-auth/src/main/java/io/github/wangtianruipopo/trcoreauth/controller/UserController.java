package io.github.wangtianruipopo.trcoreauth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @PostMapping("/hello")
    public String hello() {

        return "hello";
    }
}
