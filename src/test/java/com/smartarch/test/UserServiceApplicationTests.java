package com.smartarch.test;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.smartarch.user.UserServiceApplication;
import com.smartarch.user.mapper.UserMapper;
import com.smartarch.user.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = UserServiceApplication.class)
@Transactional
public class UserServiceApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Rollback
    public void test() throws Exception {
    	Timestamp  createTime = new Timestamp(System.currentTimeMillis());
    	String tsStr = "2020-05-09 11:49:45";   
        try {   
        	createTime = Timestamp.valueOf(tsStr);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        userMapper.insertUser("user-1", "crisschen","123456", "tenant-1", "chenzheng6809@163.com", "criss chen", "13905150362", "description1", createTime, null, "chenzheng");
        User u = userMapper.getById("user-1");
        Assert.isTrue("crisschen".equals(u.getUserName()), "username:");
    }

}
