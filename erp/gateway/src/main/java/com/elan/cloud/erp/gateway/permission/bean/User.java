package com.elan.cloud.erp.gateway.permission.bean;


import java.util.Collection;
import java.util.List;

public class User {

    private Integer id;
    private String username;
    private String password;

    private List<String> auths=null;

    public User(Integer id, String username, String password){
        this.id=id;
        this.username = username;
        this.password = password;
    }


    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



    public Collection<? extends String> getAuthorities() {
        return this.auths;
//        List<GrantedAuthority> auths = new ArrayList<>();
//        List<Role> roles = this.getRoles();
//        for (Role role : roles) {
//            auths.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return auths;
    }


    public String getPassword() {
        return this.password;
    }


    public String getUsername() {
        return this.username;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }
}
