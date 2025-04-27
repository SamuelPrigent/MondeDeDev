import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Theme } from '../interfaces/theme.interface';
// import { CreateArticle } from '../interfaces/createArticle.interface';
// import { getComment } from '../interfaces/getComment.interface';
// import { postComment } from '../interfaces/postComment.interface';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private pathService = 'themes';

  constructor(private httpClient: HttpClient) {}

  public getThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(`${environment.baseUrl}${this.pathService}`);
  }

  //   public getArticleById(id: number): Observable<Article> {
  //     return this.httpClient.get<Article>(`${environment.baseUrl}${this.pathService}/${id}`);
  //   }

  //   public postArticle(article: CreateArticle): Observable<Article> {
  //     const headers = new HttpHeaders({
  //       'Content-Type': 'application/json',
  //     });
  //     return this.httpClient.post<Article>(
  //       `${environment.baseUrl}${this.pathService}/`,
  //       JSON.stringify(article),
  //       { headers: headers }
  //     );
  //   }

  //   // get comment by article id
  //   public getCommentsByArticleId(id: number): Observable<getComment[]> {
  //     return this.httpClient.get<getComment[]>(
  //       `${environment.baseUrl}${this.pathService}/${id}/comments`
  //     );
  //   }

  //   // post comment
  //   public postCommentsForAnArticle(id: number, comment: postComment): Observable<Comment> {
  //     return this.httpClient.post<Comment>(
  //       `${environment.baseUrl}${this.pathService}/${id}/comments`,
  //       comment
  //     );
  //   }
}
