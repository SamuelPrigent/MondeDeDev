package com.openclassrooms.mddapi.dto;

public class GetThemesWithSubsInfoDTO {
	private Long id;
	private String themeName;
	private String themeDescription;
	private boolean userIsSubscribed;

	public GetThemesWithSubsInfoDTO(Long id, String themeName, String themeDescription, boolean userIsSubscribed) {
		this.id = id;
		this.themeName = themeName;
		this.themeDescription = themeDescription;
		this.userIsSubscribed = userIsSubscribed;
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

	public boolean isUserIsSubscribed() {
		return userIsSubscribed;
	}

	public void setUserIsSubscribed(boolean userIsSubscribed) {
		this.userIsSubscribed = userIsSubscribed;
	}

}
