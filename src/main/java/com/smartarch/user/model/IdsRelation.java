package com.smartarch.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdsRelation {
	private String id;
    private String _id;
    
    public IdsRelation(String id, String _id) {
        this.id = id;
        this._id = _id;
    }
}