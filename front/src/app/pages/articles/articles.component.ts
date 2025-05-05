import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { loadingDelay$ } from 'src/app/utils/loading.util';
import { Observable, BehaviorSubject } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { Title } from '@angular/platform-browser';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  loading = true;
  public articles$!: Observable<Article[]>;
  public sortOrder$ = new BehaviorSubject<'asc' | 'desc'>('desc');

  constructor(
    private titleService: Title,
    private articleService: ArticleService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    // snackbar when article created
    const state = this.router.getCurrentNavigation()?.extras.state ?? history.state;
    if (state && state['showArticleCreated']) {
      this.snackBar.open('Article créé avec succès', 'Fermer', { duration: 2000 });
    }
    // loading state
    this.loading = true;
    loadingDelay$().subscribe(() => (this.loading = false));
    // title
    this.titleService.setTitle('Articles / MDD');
    // articles
    this.articles$ = this.sortOrder$.pipe(
      switchMap(order =>
        this.articleService.getSubscribedArticles().pipe(
          map(articles =>
            articles.sort((a, b) => {
              const dateA = new Date(a.created_at).getTime();
              const dateB = new Date(b.created_at).getTime();
              return order === 'asc' ? dateA - dateB : dateB - dateA;
            })
          )
          // Si on avait d'avantage de donnée le loader serait fermé de cette mannière
          //   tap(() => (this.loading = false))
        )
      )
    );
  }

  toggleSortByDate(): void {
    const currentOrder = this.sortOrder$.value;
    this.sortOrder$.next(currentOrder === 'asc' ? 'desc' : 'asc');
  }
}
