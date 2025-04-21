import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Title } from '@angular/platform-browser';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { shareReplay } from 'rxjs/operators';

@Component({
  selector: 'app-articleDetail',
  templateUrl: './articleDetail.component.html',
  styleUrls: ['./articleDetail.component.scss'],
})
export class ArticleDetailComponent implements OnInit {
  public article$!: Observable<Article | null>;
  public usernames: { [userId: number]: Observable<string> } = {};

  constructor(
    private titleService: Title,
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
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

  public usernameById(id: number): Observable<string> {
    if (!this.usernames[id]) {
      this.usernames[id] = this.userService.getById(id).pipe(
        map(user => user.username),
        // évite de refaire la requête lorsque plusieurs async s'abonnent
        shareReplay(1)
      );
    }
    return this.usernames[id];
  }
}
