package io.github.wangtianruipopo.trcoreauth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.github.tr.common.base.entity.LoginUser;
import io.github.tr.common.base.http.HttpResult;
import io.github.wangtianruipopo.trcoreauth.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @PostMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/login")
    public HttpResult<SaTokenInfo> login(@RequestBody LoginUser loginUser) {
        return userService.login(loginUser);
    }

    @GetMapping("/logout")
    public HttpResult<Void> logout() {
        return userService.logout();
    }
}
