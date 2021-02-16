package com.smartarch.user.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Role {
	private String id;
    private String roleName;
    private String description;
    private List<OperSet> opersets;
    
    public Role(String id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}