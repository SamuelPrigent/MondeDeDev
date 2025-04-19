package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.GetArticleDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import java.util.List;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	// @GetMapping({ "/articles", "/articles/" })
	// public ResponseEntity<GetArticlesDTO> getArticles() {
	// 	return ResponseEntity.ok(new GetArticlesDTO(articleService.getArticles()));
	// }

	@GetMapping({ "/articles", "/articles/" })
	public ResponseEntity<List<GetArticleDTO>> getAllArticles() {
		// Récupérer tous les articles depuis le service
		List<Article> articles = articleService.getArticles();
		// Transforme les articles en DTO
		List<GetArticleDTO> articlesDTO = articles.stream().map(GetArticleDTO::new).toList();
		// Retourner la liste dans la réponse
		return ResponseEntity.ok(articlesDTO);
	}

	@PostMapping(value = { "/articles", "/articles/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetArticleDTO> createArticle(@RequestBody CreateArticleDTO createArticleDTO) {
		GetArticleDTO createdArticle = articleService.createArticle(createArticleDTO); // Créer l'utilisateur
		return ResponseEntity.ok(createdArticle);
	}

}