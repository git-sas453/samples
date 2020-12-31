package com.sas.bean;

import java.io.Serializable;
import java.util.Set;

public class UserBean implements Serializable {

    private String username;
    private String password;
    private Set<String> roles;
    private Set<String> permissions;

    private boolean rememberMe;

    private String phone;
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserBean() {
    }

    public UserBean(String username, String password, Set<String> roles, Set<String> permissions, String phone, String email) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.phone = phone;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
