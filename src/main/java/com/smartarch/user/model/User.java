package com.smartarch.user.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User {
    private String id;
    private String userName;
    private String passwords;
    private String tenantId;
    private String email;
    private String fullName;
    private String telephone;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp modifyTime;
    private String creator;
    private List<Role> roles;

    public User(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}