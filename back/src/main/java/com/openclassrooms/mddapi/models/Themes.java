package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entité JPA représentant un thème auquel les utilisateurs peuvent s'abonner.
 * <p>
 * Un {@code Themes} comprend :
 * <ul>
 * <li>un identifiant technique ;</li>
 * <li>un nom et une description ;</li>
 * <li>une relation <i>many-to-many</i> avec {@link User} réalisée par la table
 * de jointure <code>theme_user(theme_id, user_id)</code>.</li>
 * </ul>
 */
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