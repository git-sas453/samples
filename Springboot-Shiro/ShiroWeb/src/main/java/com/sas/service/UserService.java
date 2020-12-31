package com.sas.service;

import com.sas.bean.UserBean;
import com.sas.test.TestData;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private TestData testData;

    public UserBean getUserByName(String username) {
        logger.info("=== getUserByName");
        UserBean userBean = null;

        List<UserBean> filteredUsers = testData.getAllUsers().stream()
                .filter(user -> username.equals(user.getUsername()))
                .collect(Collectors.<UserBean>toList());

        if(null != filteredUsers && filteredUsers.size() > 0) {
            try {
                userBean = (UserBean) BeanUtils.cloneBean(filteredUsers.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userBean;
    }

    public UserBean getUserByPhone(String phone) {
        logger.info("=== getUserByPhone");
        UserBean userBean = null;
        List<UserBean> filteredUsers = testData.getAllUsers().stream()
                .filter(user -> phone.equals(user.getPhone()))
                .collect(Collectors.<UserBean>toList());

        if(null != filteredUsers && filteredUsers.size() > 0) {
            try {
                userBean = (UserBean) BeanUtils.cloneBean(filteredUsers.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userBean;
    }
}
