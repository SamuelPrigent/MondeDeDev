import { Component, OnInit } from '@angular/core';
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
  public articles$!: Observable<Article[]>;
  public sortOrder$ = new BehaviorSubject<'asc' | 'desc'>('asc');

  constructor(private titleService: Title, private articleService: ArticleService) {}

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Articles');
    // articles
    // this.articles$ = this.articleService.getArticles();
    this.articles$ = this.sortOrder$.pipe(
      switchMap(order =>
        this.articleService.getArticles().pipe(
          map(articles =>
            articles.sort((a, b) => {
              const dateA = new Date(a.created_at).getTime();
              const dateB = new Date(b.created_at).getTime();
              return order === 'asc' ? dateA - dateB : dateB - dateA;
            })
          )
        )
      )
    );
  }

  toggleSortByDate(): void {
    const currentOrder = this.sortOrder$.value;
    this.sortOrder$.next(currentOrder === 'asc' ? 'desc' : 'asc');
  }
}
