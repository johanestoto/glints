package com.glints.toto.model;

import javax.validation.constraints.NotNull;

/**
 * Class for Player user session
 * @author Johanes Toto Indarto
 */
public class User {
	
	@NotNull(message = "{User.Name.NotEmpty}")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
