package com.openclassrooms.mddapi.dto;

public class CreateArticleDTO {
	private String title;
	private String description;
	private String theme;
	private Long authorId;

	// Constructeur par défaut
	public CreateArticleDTO() {
	}

	// Constructeur avec arguments
	public CreateArticleDTO(String title, String description, String theme, Long authorId) {
		this.title = title;
		this.description = description;
		this.theme = theme;
		this.authorId = authorId;
	}

	// Getters
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getTheme() {
		return theme;
	}

	public Long getAuthorId() {
		return authorId;
	}

	// Setters
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	// Méthode de validation simple (exemple)
	public void validate() {
		if (title == null || title.isBlank())
			throw new IllegalArgumentException("Le titre est obligatoire");
		if (description == null || description.isBlank())
			throw new IllegalArgumentException("La description est obligatoire");
		if (theme == null || theme.isBlank())
			throw new IllegalArgumentException("Le thème est obligatoire");
		if (authorId == null)
			throw new IllegalArgumentException("L'auteur est obligatoire");
	}
}