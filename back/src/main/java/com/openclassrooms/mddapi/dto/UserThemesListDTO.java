package com.openclassrooms.mddapi.dto;

import java.util.List;

public class UserThemesListDTO {
	private List<String> themes;

	public UserThemesListDTO(List<String> themes) {
		this.themes = themes;
	}

	public List<String> getThemes() {
		return themes;
	}

	public void setThemes(List<String> themes) {
		this.themes = themes;
	}
}