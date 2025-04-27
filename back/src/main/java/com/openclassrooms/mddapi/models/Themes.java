package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "themes")
public class Themes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 1500)
	private String themeName;

	@Column(nullable = false, length = 3000)
	private String themeDescription;

	@ManyToMany
	@JoinTable(name = "theme_user", joinColumns = @JoinColumn(name = "theme_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;

	public Themes() {
	}

	public Themes(String themeName, String themeDescription, Set<User> users) {
		this.themeName = themeName;
		this.themeDescription = themeDescription;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getThemeDescription() {
		return themeDescription;
	}

	public void setThemeDescription(String themeDescription) {
		this.themeDescription = themeDescription;
	}
}