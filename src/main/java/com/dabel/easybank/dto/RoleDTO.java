package com.dabel.easybank.dto;

import lombok.Data;

@Data
public class RoleDTO {

	private int roleId;
	
	private String username;
	private String name;
	
	public RoleDTO() {
		
	}
	
	public RoleDTO(String username, String name) {

		this.username = username;
		this.name = name;
	}
	
}
