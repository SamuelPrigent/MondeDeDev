package com.openclassrooms.mddapi.service;

import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.GetArticleDTO;
import com.openclassrooms.mddapi.dto.GetCommentDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.dto.PostCommentDTO;
import com.openclassrooms.mddapi.models.Comment;

import java.util.Optional;
import java.util.List;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	// Récupérer un article par son id
	public Optional<Article> getById(Long id) {
		return articleRepository.findById(id);
	}

	// Récupérer tous les articles
	public List<Article> getArticles() {
		return articleRepository.findAll();
	}

	// Create Article
	public GetArticleDTO createArticle(CreateArticleDTO request) {
		// TO DO il faudra check que le token correspond bien à l'id pour qui on créé le post ??
		// validation des données
		request.validate();
		// get author via authorId
		User author = userRepository.findById(request.getAuthorId())
				.orElseThrow(() -> new IllegalArgumentException("Auteur non trouvé"));
		// create article object
		Article article = new Article();
		article.setTitle(request.getTitle());
		article.setDescription(request.getDescription());
		article.setTheme(request.getTheme());
		article.setAuthor(author); // objet User filtré par le DTO => id
		// save
		Article savedArticle = articleRepository.save(article);
		return new GetArticleDTO(savedArticle);
	}

	// Retourne une liste de GetCommentDTO (ou List<Comment> si tu veux transformer ensuite)
	public List<GetCommentDTO> getCommentsByArticle(Long articleId) {
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
		List<Comment> comments = commentRepository.findByArticle(article);
		return comments.stream().map(GetCommentDTO::new).toList();
	}

	// post comment 
	public Comment postComment(Long articleId, PostCommentDTO dto) {
		// Vérifier que l'article existe
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
		// Vérifier que l'utilisateur existe
		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

		Comment comment = new Comment();
		comment.setArticle(article);
		comment.setUser(user);
		comment.setComment(dto.getComment());
		return commentRepository.save(comment);
	}

}