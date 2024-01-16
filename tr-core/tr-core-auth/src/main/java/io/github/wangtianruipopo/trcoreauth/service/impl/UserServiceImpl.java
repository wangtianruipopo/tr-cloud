package io.github.wangtianruipopo.trcoreauth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.github.tr.common.base.entity.LoginUser;
import io.github.tr.common.base.http.HttpResult;
import io.github.wangtianruipopo.trcoreauth.service.IUserService;
import io.github.wangtianruipopo.trcoreauthconfig.core.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final ILoginService loginService;

    @Override
    public HttpResult<SaTokenInfo> login(LoginUser loginUser) {
        LoginUser user = loginService.getLoginUser(loginUser.getUsername(), loginUser.getPassword());
        if (user == null) {
            return HttpResult.error("用户名或密码错误！");
        }
        // 第1步，先登录上
        StpUtil.login(user.getId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return HttpResult.ok(tokenInfo);
    }
}
