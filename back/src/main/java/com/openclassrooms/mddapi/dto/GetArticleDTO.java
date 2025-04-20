package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.models.Article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;

public class GetArticleDTO {
	private Long id;
	private String title;
	private String description;
	private String theme;
	private Long authorId;
	private String authorUsername;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	// Constructeur par défaut
	public GetArticleDTO() {
	}

	// Constructeur à partir d'une entité Article
	public GetArticleDTO(Article article) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.id = article.getId();
		this.title = article.getTitle();
		this.description = article.getDescription();
		this.theme = article.getTheme();
		this.authorId = article.getAuthor().getId();
		this.authorUsername = article.getAuthor().getUsername();

		// Gestion des dates nulles
		if (article.getCreatedAt() != null) {
			this.createdAt = article.getCreatedAt().format(formatter);
		} else {
			this.createdAt = "non défini";
		}

		if (article.getUpdatedAt() != null) {
			this.updatedAt = article.getUpdatedAt().format(formatter);
		} else {
			this.updatedAt = "non défini";
		}
	}

	// Getters & Setters
	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}