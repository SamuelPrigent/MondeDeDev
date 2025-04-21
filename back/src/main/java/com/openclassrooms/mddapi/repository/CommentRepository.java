package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment; // le bon models ?

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByArticle(Article article);
}