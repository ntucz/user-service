package com.smartarch.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.smartarch.user.model.OperSet;

public interface OperSetMapper {

    OperSet getById(@Param("id") String id);
    
    OperSet getByOpersetName(@Param("opersetname") String opersetName);
    
    void updateOperSet(@Param("description") String description);

    void insertOperSet(@Param("id") String id, @Param("opersetname") String opersetName, @Param("description") String description);
    
    void deleteOperSet(@Param("id") String id);
    
}
