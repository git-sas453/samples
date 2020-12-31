package com.sas.controller;

import com.sas.bean.UserBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public String login(UserBean userBean) {

        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated() && !currentUser.isRemembered()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userBean.getUsername(), userBean.getPassword());
            token.setRememberMe(true);
            try {
                currentUser.login(token);

                currentUser.getSession().setAttribute("currentUser", currentUser.getPrincipal());
                logger.info("login success");
                return "login success";
            } catch (UnknownAccountException uae) {
                logger.info("There is no user with username of " + token.getPrincipal());
                return "There is no user with username of " + token.getPrincipal();
            } catch (IncorrectCredentialsException ice) {
                logger.info("Password for account " + token.getPrincipal() + " was incorrect!");
                return "Password for account " + token.getPrincipal() + " was incorrect!";
            } catch (LockedAccountException lae) {
                logger.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
                return "The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.";
            }
            catch (AuthenticationException ae) {
                return "login fail";
            }
        }
        return "login success";
    }

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session.getAttribute("currentUser");
    }

    @RequestMapping("/logout")
    public void logout() {
        logger.info("logout");
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @RequestMapping("unauthed")
    public String unauthed() {
        return "unauthed";
    }

}
