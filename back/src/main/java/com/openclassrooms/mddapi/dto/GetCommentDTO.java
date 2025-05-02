package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.models.Comment;

public class GetCommentDTO {
	private String comment;
	private String username;

	public GetCommentDTO(Comment comment) {
		this.comment = comment.getComment();
		this.username = comment.getUser().getUsername();
	}

	public String getComment() {
		return comment;
	}

	public String getUsername() {
		return username;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}