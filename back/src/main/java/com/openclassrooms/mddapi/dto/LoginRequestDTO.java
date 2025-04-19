package com.openclassrooms.mddapi.dto;

public class LoginRequestDTO {
	private String userId;
	private String password;

	public LoginRequestDTO() {
	}

	public LoginRequestDTO(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginRequestDTO{userId='" + userId + "', passwordLength=" + (password != null ? password.length() : 0)
				+ '}';
	}
}