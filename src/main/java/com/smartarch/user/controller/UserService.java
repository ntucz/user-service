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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartarch.user.common.UserUtil;
import com.smartarch.user.mapper.UserMapper;
import com.smartarch.user.model.IdsRelation;
import com.smartarch.user.model.Response;
import com.smartarch.user.model.Role;
import com.smartarch.user.model.User;
import com.smartarch.user.token.PassToken;
import com.smartarch.user.token.TokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/users")
public class UserService {
 
	@Autowired
    private UserMapper userMapper;
	
	@ApiOperation(value = "用户登录")
	@PassToken
    @PostMapping("/login")
    public Response login(@RequestBody User user) {
		log.info("Userservice login:{}", user);
		Response response = new Response();
		try {
			User dbUser = userMapper.getByUsername(user.getUserName());
			if(dbUser != null && dbUser.getPasswords().equals(user.getPasswords())) {
				String token = TokenService.getToken(dbUser);
				response.setCode(HttpStatus.OK.value());
				response.setMessage(token);
				log.info("Userservice login success");
			} else {
				response.setCode(HttpStatus.UNAUTHORIZED.value());
			}
		} catch(Exception e) {
			log.error("login error:", e);
			response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(e.getMessage());
		}
        return response;
    }

	@ApiOperation(value = "增加用户")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Response insertUser(@RequestBody User user) {
		log.info("Userservice insertUser:{}", user.toString());
		User dbUser = userMapper.getByUsername(user.getUserName());
		if(dbUser == null) {
			String userId = UUID.randomUUID().toString();
			userMapper.insertUser(userId, user.getUserName(), user.getPasswords(), user.getTenantId(), user.getEmail(), user.getFullName(),
					user.getTelephone(), user.getDescription(), user.getCreateTime(), user.getModifyTime(), user.getCreator());
			List<Role> roles = user.getRoles();
			if(!UserUtil.isCollectionEmpty(roles)) {
				for(Role role : roles) {
					userMapper.insertUserRole(userId, role.getId());
				}
			}
			log.info("Userservice insertUser success");
			return new Response(HttpStatus.CREATED.value());
		} else {
			Response response = new Response(HttpStatus.BAD_REQUEST.value());
			response.setMessage(UserUtil.getStrFormat("User {0} exists.", user.getUserName()));
			return response;
		}
    }
    
	@ApiOperation(value = "删除用户")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/")
    public Response deleteUser(String id) {
		log.info("Userservice deleteUser id:{}", id);
    	userMapper.deleteUser(id);
    	userMapper.deleteUserRoleById(id);
    	log.info("Userservice deleteUser end");
    	return new Response(HttpStatus.CREATED.value());
    }
 
	@ApiOperation(value = "修改用户")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/")
    public Response updateUser(@RequestBody User user) {
		log.info("Userservice updateUser user:{}", user);
		userMapper.updateUser(user.getPasswords(), user.getTenantId(), user.getEmail(), 
				user.getFullName(), user.getTelephone(), user.getDescription(), user.getModifyTime());
		String userId = user.getId();
		//查询角色列表，用于比较下发的角色列表
		List<IdsRelation> dbRoles = userMapper.getRolesById(userId);
		List<String> ids = new ArrayList<String>();
		user.getRoles().forEach((role) -> {
			ids.add(((Role)role).getId());
		});
		List<String> dbIds = new ArrayList<String>();
		dbRoles.forEach((relation) -> {
			dbIds.add(((IdsRelation)relation).get_id());
		});
		Iterator<String> dbIdItr = dbIds.iterator();
		while(dbIdItr.hasNext()) {
			String dbId = dbIdItr.next();
			if(!ids.contains(dbId)) {
				userMapper.deleteUserRole(userId, dbId);
			} else {
				ids.remove(dbId);
			}
		}
		if(!ids.isEmpty()) {
			for(String id : ids) {
				userMapper.insertUserRole(userId, id);
			}
		}
		log.info("Userservice updateUser end");
    	return new Response(HttpStatus.CREATED.value());
    }
	
	@ApiOperation(value = "查询用户-根据id")
    @GetMapping("/")
    public Response queryById(@RequestParam("id") String id) {
		log.info("Userservice queryById id:{}", id);
        User user = userMapper.getById(id);
        Response res = new Response(HttpStatus.OK.value());
        res.setObj(user);
        log.info("Userservice queryById end");
        return res;
    }
}