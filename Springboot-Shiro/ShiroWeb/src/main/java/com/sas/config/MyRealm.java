package com.sas.config;

import com.sas.bean.UserBean;
import com.sas.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(MyRealm.class);

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("===== enter doGetAuthorizationInfo");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissions =  ((UserBean)principalCollection.getPrimaryPrincipal()).getPermissions();
        Set<String> roles =  ((UserBean)principalCollection.getPrimaryPrincipal()).getRoles();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("===== enter doGetAuthenticationInfo");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        UserBean userBean = userService.getUserByName(username);
        if(userBean == null) return null;

        ByteSource salt = ByteSource.Util.bytes("s123");
        // 密码验证由shiro完成
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userBean, userBean.getPassword(), salt,"myRealm");
        return simpleAuthenticationInfo;
    }
}
