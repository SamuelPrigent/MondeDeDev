import { Component, OnInit } from '@angular/core';
import { loadingDelay$ } from 'src/app/utils/loading.util';
import { first } from 'rxjs';
import { Observable, BehaviorSubject } from 'rxjs';
import { map, switchMap, tap } from 'rxjs/operators';
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

  constructor(private titleService: Title, private articleService: ArticleService) {}

  ngOnInit(): void {
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
          //   tap(() => (this.loading = false)) // si on avait plus de données
        )
      )
    );
    // subscribe pour démarrer le flux malgré la condition dans le template
    // this.articles$.pipe(first()).subscribe();
  }

  toggleSortByDate(): void {
    const currentOrder = this.sortOrder$.value;
    this.sortOrder$.next(currentOrder === 'asc' ? 'desc' : 'asc');
  }
}
