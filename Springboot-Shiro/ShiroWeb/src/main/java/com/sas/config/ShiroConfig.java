package com.sas.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ShiroConfig {

    /*@Bean
    public Realm myRealm() { //数据源
        return new MyRealm();
    }*/

    @Bean
    public DefaultSecurityManager mySecurityManager(AuthorizingRealm myRealm, AuthenticatingRealm mobileRealm) { //流程控制
        DefaultSecurityManager sm = new DefaultWebSecurityManager();

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(3); // 设置哈希次数，对密码的hashcode再进行hash
        credentialsMatcher.setStoredCredentialsHexEncoded(true);  //数据源的用户密码是否已经加密过
        //在Realm实现类中加盐
        myRealm.setCredentialsMatcher(credentialsMatcher);
        mobileRealm.setCredentialsMatcher(credentialsMatcher);
        //sm.setRealms(Arrays.asList(myRealm, mobileRealm));



        /*
        * 未设置时使用的是 AtLeastOneSuccessfulStrategy，用户名密码或者电话密码验证中有一项成功即可
        * 使用AtLeastOneSuccessfulStrategy 和 AllSuccessfulStrategy 时，都会调用所有realm的认证方法
        * 只有在使用FirstSuccessfulStrategy 且 setStopAfterFirstSuccess 时，有一个realm返回成功就停止调用剩下的realm
        * */


        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(myRealm, mobileRealm));
        //authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy()); // 设置多数据源认证策略
        FirstSuccessfulStrategy strategy = new FirstSuccessfulStrategy();
        strategy.setStopAfterFirstSuccess(true);
        authenticator.setAuthenticationStrategy(strategy); // 设置多数据源认证策略
        sm.setAuthenticator(authenticator);


        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = new SimpleCookie("rememberme");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60);
        rememberMeManager.setCookie(cookie);
        sm.setRememberMeManager(rememberMeManager);


        //缓存认证, 缓存用户的权限信息，不会重复进入Realm的doGetAuthorizationInfo方法
        MemoryConstrainedCacheManager memoryConstrainedCacheManager = new MemoryConstrainedCacheManager();
        sm.setCacheManager(memoryConstrainedCacheManager);

        sm.setRealm(myRealm);
        return sm;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager mySecurityManager) { //请求过滤器
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(mySecurityManager);

        // 路径资源保护
       /* Map<String, String> filterMap = new HashMap<>();
        // ** 代表多级路径   * 代表单级路径 authc代表需要登录才能访问
        filterMap.put("/mobile/**", "authc,perms[mobile]");
        filterMap.put("/salary/**", "authc,perms[salary]");

        //filterMap.put("/common/logout", "logout");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        factoryBean.setLoginUrl("/html/login.html");
        factoryBean.setUnauthorizedUrl("/common/unauthed");*/

        return factoryBean;
    }
}
