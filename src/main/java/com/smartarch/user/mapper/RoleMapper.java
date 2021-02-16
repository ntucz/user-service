package com.smartarch.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartarch.user.model.IdsRelation;
import com.smartarch.user.model.Role;

public interface RoleMapper {

    Role getById(@Param("id") String id);
    
    Role getByRoleName(@Param("rolename") String rolename);
    
    List<IdsRelation> getOpersetsById(@Param("id") String id);

    void insertRole(@Param("id") String id, @Param("rolename") String rolename, @Param("description") String description);
    
    void updateRole(@Param("description") String description);
    
    void deleteRole(@Param("id") String id);
    
    void deleteRoleOpersetsById(@Param("roleid") String roleid);
    
    void deleteRoleOperset(@Param("roleid") String roleid, @Param("opersetid") String opersetid);
    
    void insertRoleOperset(@Param("roleid") String roleid, @Param("opersetid") String opersetid); 
    
}
