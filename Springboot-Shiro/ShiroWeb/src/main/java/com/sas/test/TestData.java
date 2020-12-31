package com.sas.test;

import com.sas.bean.UserBean;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TestData {
    private List<UserBean> allUsers;

    public List<UserBean> getAllUsers() {
        if(null == allUsers) {
            allUsers = new ArrayList<UserBean>();


            Set<String> adminSet1 = new HashSet<>();
            adminSet1.add("admin");

            Set<String>  adminSet2 = new HashSet<>();
            adminSet2.add("mobile");
            adminSet2.add("salary");

            Set<String>  managerSet1 = new HashSet<>();
            managerSet1.add("manager");

            Set<String>  managerSet2 = new HashSet<>();
            managerSet2.add("salary");

            Set<String>  workerSet1 = new HashSet<>();
            workerSet1.add("worker");

            Set<String>  workerSet2 = new HashSet<>();
            workerSet2.add("");

            String salt = "s123";  //加盐

            String adminPassword = new SimpleHash("MD5", ByteSource.Util.bytes("admin"), salt, 3).toString();
            String managerPassword = new SimpleHash("MD5", ByteSource.Util.bytes("manager"), salt, 3).toString();
            String workerPassword = new SimpleHash("MD5", ByteSource.Util.bytes("worker"), salt, 3).toString();

            allUsers.add(new UserBean("admin", adminPassword, adminSet1, adminSet2, "13900000001", "admin@sas.com"));
            allUsers.add(new UserBean("manager", managerPassword, managerSet1, managerSet2, "13900000002", "manager@sas.com"));
            allUsers.add(new UserBean("worker", workerPassword, workerSet1, workerSet2, "13900000003", "worker@sas.com"));
        }

        return allUsers;
    }
}
