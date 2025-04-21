package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.models.Comment;

public class GetCommentDTO {
	private Long id;
	private String comment;
	private Long userId;
	private String username;

	// Constructeur à partir de Comment
	public GetCommentDTO(Comment comment) {
		this.id = comment.getId();
		this.comment = comment.getComment();
		this.userId = comment.getUser().getId();
		this.username = comment.getUser().getUsername();
	}

	public Long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}