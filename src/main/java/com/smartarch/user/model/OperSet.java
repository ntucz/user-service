package com.smartarch.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperSet {
	private String id;
    private String opersetName;
    private String description;
    
    public OperSet(String id, String opersetName) {
        this.id = id;
        this.opersetName = opersetName;
    }
}