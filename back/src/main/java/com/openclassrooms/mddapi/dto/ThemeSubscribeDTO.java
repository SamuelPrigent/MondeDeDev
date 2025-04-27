package com.openclassrooms.mddapi.dto;

public class ThemeSubscribeDTO {
	private Long themeId;

	public ThemeSubscribeDTO() {
	}

	public ThemeSubscribeDTO(Long themeId) {
		this.themeId = themeId;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
}
