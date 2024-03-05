package io.github.wangtianruipopo.trcoreauth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.github.tr.common.base.entity.LoginUser;
import io.github.tr.common.base.http.HttpResult;

public interface IUserService {
    HttpResult<SaTokenInfo> login(LoginUser loginUser);

    HttpResult<Void> logout();
}
