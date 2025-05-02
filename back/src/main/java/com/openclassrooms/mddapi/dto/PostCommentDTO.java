package com.openclassrooms.mddapi.dto;

public class PostCommentDTO {
	private String comment;

	public PostCommentDTO() {
	}

	public PostCommentDTO(Long userId, String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}