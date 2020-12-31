package com.sas.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class MobileController {

    @RequiresPermissions("mobile")
    @RequestMapping("/query")
    public String query() {
        /*Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isPermitted("mobile")) {
            return "mobile";
        }
        return "error";*/
        return "mobile";
    }
}
