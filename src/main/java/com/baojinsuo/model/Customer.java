package com.baojinsuo.model;

import com.baojinsuo.base.BaseModel;
import com.baojinsuo.common.SafeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Customer")
public class Customer extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username; // username

    private String nickname; // full name

    @Column(unique = true)
    private String mobile;

    private String password; // should be hashed, but doesn't matter in our example

    @JsonIgnore
    private Integer createdTime;

    @JsonIgnore
    private Integer updatedTime;

    @JsonIgnore
    private Integer lastPasswordReset;

    private String[] roles;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @PrePersist
    public void onCreated() {
        if (SafeUtils.isNull(username)){
            username = "vip" + mobile;
        }
        createdTime =
        updatedTime =
        lastPasswordReset = SafeUtils.getCurrentUnixTime();
    }

    @PreUpdate
    public void getUpdatedTime() {
        updatedTime = SafeUtils.getCurrentUnixTime();
    }

    public String[] getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCreatedTime(Integer createdTime) {
        this.createdTime = createdTime;
    }

    public void setUpdatedTime(Integer updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Integer getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Integer lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }

    public String getPrincipal(){
        return SafeUtils.isNotNull(getUsername())? getUsername() : getMobile();
    }

    // username works as ID here, if there is normal ID column, use that of course
    // equals/hashCode is very important for AuthenticationServiceImpl#tokens to work properly
    @Override
    public boolean equals(Object o) {
        return this == o
                || o != null && o instanceof Customer
                && Objects.equals(username, ((Customer) o).username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
