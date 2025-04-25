package com.openclassrooms.mddapi.dto;

public class GetThemesDTO {
	private Long id;
	private String themeName;

	public GetThemesDTO(Long id, String themeName) {
		this.id = id;
		this.themeName = themeName;
	}

	public Long getId() {
		return id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
}
