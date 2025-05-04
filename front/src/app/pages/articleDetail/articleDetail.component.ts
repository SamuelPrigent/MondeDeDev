import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { getComment } from 'src/app/interfaces/getComment.interface';
import { postComment } from 'src/app/interfaces/postComment.interface';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-articleDetail',
  templateUrl: './articleDetail.component.html',
  styleUrls: ['./articleDetail.component.scss'],
})
export class ArticleDetailComponent implements OnInit {
  public article$!: Observable<Article | null>;
  public comments$!: Observable<getComment[]>;
  public commentForm: FormGroup;
  loading = true;

  constructor(
    private titleService: Title,
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.commentForm = this.fb.group({
      comment: ['', [() => null]], // champ présent, toujours valide
    });
  }

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Article detail');
    // articles
    const articleId = +this.route.snapshot.paramMap.get('id')!;
    if (articleId != null && articleId != undefined) {
      // get article
      this.article$ = this.articleService.getArticleById(articleId).pipe(
        catchError(e => {
          this.router.navigate(['/404']);
          return of(null);
        })
      );
      //   get comments
      this.comments$ = this.articleService.getCommentsByArticleId(articleId);
    }
  }

  public back(): void {
    window.history.back();
  }

  public postComment(id: number, post: postComment) {
    this.articleService.postCommentsForAnArticle(id, post).subscribe({
      next: result => {
        // Action à faire après succès, ex: rafraîchir les commentaires
        this.comments$ = this.articleService.getCommentsByArticleId(id);
      },
      error: err => {
        console.error("Erreur lors de l'envoi du commentaire", err);
      },
    });
  }

  public submitComment(): void {
    if (this.commentForm.invalid) {
      return;
    }

    const comment = this.commentForm.value.comment.trim();
    const articleId = +this.route.snapshot.paramMap.get('id')!;
    const post: postComment = { comment };

    this.postComment(articleId, post);
    this.commentForm.reset();
  }
}
