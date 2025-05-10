package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;

/**
 * Entité JPA représentant un commentaire.
 * <p>
 * Chaque {@code Comment} est :
 * <ul>
 * <li>associé à un {@link Article} (relation {@code ManyToOne}) ;</li>
 * <li>écrit par un {@link User} (relation {@code ManyToOne}).</li>
 * </ul>
 */
@Entity
@Table(name = "article_comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false, length = 1500)
	private String comment;

	public Comment() {
	}

	public Comment(Article article, User user, String comment) {
		this.article = article;
		this.user = user;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}