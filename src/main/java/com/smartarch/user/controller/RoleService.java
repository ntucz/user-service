package com.smartarch.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartarch.user.common.UserUtil;
import com.smartarch.user.mapper.RoleMapper;
import com.smartarch.user.model.IdsRelation;
import com.smartarch.user.model.OperSet;
import com.smartarch.user.model.Response;
import com.smartarch.user.model.Role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/roles")
public class RoleService {
 
	@Autowired
    private RoleMapper roleMapper;
	

	@ApiOperation(value = "增加角色")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Response insertRole(@RequestBody Role role) {
		log.info("RoleService insertRole:{}", role.toString());
		Role dbRole = roleMapper.getByRoleName(role.getRoleName());
		if(dbRole == null) {
			String roleId = UUID.randomUUID().toString();
			roleMapper.insertRole(roleId, role.getRoleName(), role.getDescription());
			List<OperSet> opersets = role.getOpersets();
			if(!UserUtil.isCollectionEmpty(opersets)) {
				for(OperSet operset : opersets) {
					roleMapper.insertRoleOperset(roleId, operset.getId());
				}
			}
			log.info("RoleService insertRole success");
			return new Response(HttpStatus.CREATED.value());
		} else {
			Response response = new Response(HttpStatus.BAD_REQUEST.value());
			response.setMessage(UserUtil.getStrFormat("Role {0} exists.", role.getRoleName()));
			return response;
		}
        
    }
    
	@ApiOperation(value = "删除角色")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/")
    public Response deleteRole(String id) {
		log.info("RoleService deleteRole id:{}", id);
		roleMapper.deleteRole(id);
		roleMapper.deleteRoleOpersetsById(id);
    	log.info("RoleService deleteRole end");
    	return new Response(HttpStatus.CREATED.value());
    }
 
	@ApiOperation(value = "修改角色")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/")
    public Response updateRole(@RequestBody Role role) {
		log.info("RoleService updateRole role:{}", role);
		roleMapper.updateRole(role.getDescription());
		String roleId =  role.getId();
		//查询操作集列表，用于比较下发的操作集列表
		List<IdsRelation> dbRoles = roleMapper.getOpersetsById(roleId);
		List<String> ids = new ArrayList<String>();
		role.getOpersets().forEach((operset) -> {
			ids.add(((OperSet)operset).getId());
		});
		List<String> dbIds = new ArrayList<String>();
		dbRoles.forEach((relation) -> {
			dbIds.add(((IdsRelation)relation).get_id());
		});
		Iterator<String> dbIdItr = dbIds.iterator();
		while(dbIdItr.hasNext()) {
			String dbId = dbIdItr.next();
			if(!ids.contains(dbId)) {
				roleMapper.deleteRoleOperset(roleId, dbId);
			} else {
				ids.remove(dbId);
			}
		}
		if(!ids.isEmpty()) {
			for(String id : ids) {
				roleMapper.insertRoleOperset(roleId, id);
			}
		}
		log.info("RoleService updateRole end");
    	return new Response(HttpStatus.CREATED.value());
    }
 
	@ApiOperation(value = "查询角色-根据id")
    @GetMapping("/")
    public Response queryById(String id) {
		log.info("RoleService queryById id:{}", id);
		Role role = roleMapper.getById(id);
        Response res = new Response(HttpStatus.OK.value());
        res.setObj(role);
        log.info("RoleService queryById end");
        return res;
    }
}