import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
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

  constructor(private titleService: Title, private articleService: ArticleService) {}

  ngOnInit(): void {
    // title
    this.titleService.setTitle('Articles');
    // articles
    this.articles$ = this.articleService.getArticles();
  }

  toggleSortByDate(): void {}
}
