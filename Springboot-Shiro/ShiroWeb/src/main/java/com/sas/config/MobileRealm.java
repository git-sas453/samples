package com.sas.config;

import com.sas.bean.UserBean;
import com.sas.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MobileRealm extends AuthenticatingRealm {

    private Logger logger = LoggerFactory.getLogger(MobileRealm.class);

    @Resource
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("===== enter mobile doGetAuthenticationInfo");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String phone = token.getUsername();

        UserBean userBean = userService.getUserByPhone(phone);
        if(userBean == null) return null;

        ByteSource salt = ByteSource.Util.bytes("s123");
        // 密码验证由shiro完成
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userBean, userBean.getPassword(), salt,"myRealm");
        return simpleAuthenticationInfo;
    }
}
