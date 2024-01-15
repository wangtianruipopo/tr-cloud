package io.github.wangtianruipopo.trcoreauthconfig.core;

import io.github.tr.common.base.entity.LoginUser;

public interface ILoginService {
    LoginUser getLoginUser(String username, String password);
}
