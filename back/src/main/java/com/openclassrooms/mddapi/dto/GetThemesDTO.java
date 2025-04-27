package com.openclassrooms.mddapi.dto;

public class GetThemesDTO {
	private Long id;
	private String themeName;
	private String themeDescription;

	public GetThemesDTO(Long id, String themeName, String themeDescription) {
		this.id = id;
		this.themeName = themeName;
		this.themeDescription = themeDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeDescription() {
		return themeDescription;
	}

	public void setThemeDescription(String themeDescription) {
		this.themeDescription = themeDescription;
	}

}
