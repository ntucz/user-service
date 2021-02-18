package com.smartarch.user.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartarch.user.common.UserUtil;
import com.smartarch.user.mapper.OperSetMapper;
import com.smartarch.user.model.OperSet;
import com.smartarch.user.model.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "操作集管理接口")
@RestController
@RequestMapping("/opersets")
public class OperSetService {
 
	@Autowired
    private OperSetMapper opersetMapper;
	
	@ApiOperation(value = "增加操作集")
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Response insertOperset(@RequestBody OperSet operset) {
		log.info("OperSetService insertOperset:{}", operset.toString());
		OperSet dbOperSet = opersetMapper.getByOpersetName(operset.getOpersetName());
		if(dbOperSet == null) {
			opersetMapper.insertOperSet(UUID.randomUUID().toString(), operset.getOpersetName(), operset.getDescription());
			log.info("OperSetService insertOperset success");
			return new Response(HttpStatus.CREATED.value());
		} else {
			Response response = new Response(HttpStatus.BAD_REQUEST.value());
			response.setMessage(UserUtil.getStrFormat("OperSet {0} exists.", operset.getOpersetName()));
			return response;
		}
    }
    
	@ApiOperation(value = "删除操作集")
	@ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/")
    public Response deleteOpserSet(String id) {
		log.info("OperSetService deleteOpserSet id:{}", id);
		opersetMapper.deleteOperSet(id);
    	log.info("OperSetService deleteOpserSet end");
    	return new Response(HttpStatus.CREATED.value());
    }
 
	@ApiOperation(value = "修改操作集")
	@ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/")
    public Response updateUser(@RequestBody OperSet operset) {
		opersetMapper.updateOperSet(operset.getDescription());
    	return new Response(HttpStatus.CREATED.value());
    }
 
	@ApiOperation(value = "查询操作集-根据id")
    @GetMapping("/")
    public Response queryById(String id) {
		log.info("OperSetService queryById id:{}", id);
		OperSet operset = opersetMapper.getById(id);
        Response res = new Response(HttpStatus.OK.value());
        res.setObj(operset);
        log.info("OperSetService queryById end");
        return res;
    }
}