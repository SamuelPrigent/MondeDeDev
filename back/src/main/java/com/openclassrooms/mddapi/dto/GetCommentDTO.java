package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.models.Comment;

public class GetCommentDTO {
	private Long id;
	private String comment;
	private String username;
	// private Long userId;

	// Constructeur à partir de Comment
	public GetCommentDTO(Comment comment) {
		this.id = comment.getId();
		this.comment = comment.getComment();
		this.username = comment.getUser().getUsername();
		// this.userId = comment.getUser().getId(); // TODO (logique)
	}

	// public Long getUserId() {
	// 	return userId;
	// }

	// public void setUserId(Long userId) {
	// 	this.userId = userId;
	// }

	public Long getId() {
		return id;
	}

	public String getComment() {
		return comment;
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

	public void setUsername(String username) {
		this.username = username;
	}

}