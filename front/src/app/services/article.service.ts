import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Article } from '../interfaces/article.interface';
import { CreateArticle } from '../interfaces/createArticle.interface';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private pathService = 'articles';

  constructor(private httpClient: HttpClient) {}

  public getArticles(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(`${environment.baseUrl}${this.pathService}`);
  }

  // get id in params
  public getArticleById(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${environment.baseUrl}${this.pathService}/${id}`);
  }

  public postArticle(article: CreateArticle): Observable<Article> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.httpClient.post<Article>(
      `${environment.baseUrl}${this.pathService}/`,
      JSON.stringify(article),
      { headers: headers }
    );
  }
}
