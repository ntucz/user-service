package com.smartarch.user.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartarch.user.model.IdsRelation;
import com.smartarch.user.model.User;

public interface UserMapper {

    User getById(@Param("id") String id);
    
    User getByUsername(@Param("username") String username);
    
    List<IdsRelation> getRolesById(@Param("id") String id);

    void insertUser(@Param("id") String id, @Param("username") String username, @Param("passwords") String passwords,
    		@Param("tenantid") String tenantid,@Param("email") String email,@Param("fullname") String fullname,
    		@Param("telephone") String telephone,@Param("description") String description,@Param("createtime") Timestamp createtime,
    		@Param("modifytime") Timestamp modifytime,@Param("creator") String creator);
    
    void updateUser(@Param("passwords") String passwords, @Param("tenantid") String tenantid,@Param("email") String email,@Param("fullname") String fullname,
    		@Param("telephone") String telephone,@Param("description") String description, @Param("modifytime") Timestamp modifytime);
    
    void deleteUser(@Param("id") String id);
    
    void deleteUserRoleById(@Param("userid") String userid);
    
    void deleteUserRole(@Param("userid") String userid, @Param("roleid") String roleid);
    
    void insertUserRole(@Param("userid") String userid, @Param("roleid") String roleid); 
    
}
