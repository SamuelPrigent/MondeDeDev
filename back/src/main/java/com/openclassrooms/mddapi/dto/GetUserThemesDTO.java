package com.openclassrooms.mddapi.dto;

public class GetUserThemesDTO {
	private String themeName;

	public GetUserThemesDTO(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
}
