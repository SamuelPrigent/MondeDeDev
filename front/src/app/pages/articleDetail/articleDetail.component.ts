import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-articleDetail',
  templateUrl: './articleDetail.component.html',
  styleUrls: ['./articleDetail.component.scss'],
})
export class ArticleDetailComponent implements OnInit {
  public article$!: Observable<Article | null>;

  constructor(
    private titleService: Title,
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Article detail');
    // articles
    const articleId = +this.route.snapshot.paramMap.get('id')!;
    if (articleId != null && articleId != undefined) {
      this.article$ = this.articleService.getArticleById(articleId).pipe(
        catchError(e => {
          this.router.navigate(['/articles']);
          return of(null);
        })
      );
    }
  }

  public back(): void {
    window.history.back();
  }
}
