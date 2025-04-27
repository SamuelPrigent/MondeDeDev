import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Theme } from '../interfaces/theme.interface';
import { ThemeSubInfo } from '../interfaces/themeSubInfo.interface';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private pathService = 'themes';

  constructor(private httpClient: HttpClient) {}

  // for account page (me.component)
  public getThemesByUserId(userId: number): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(`${environment.baseUrl}${this.pathService}/${userId}`);
  }

  public getThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(`${environment.baseUrl}${this.pathService}`);
  }

  public getThemesSubsInfo(): Observable<ThemeSubInfo[]> {
    return this.httpClient.get<ThemeSubInfo[]>(`${environment.baseUrl}${this.pathService}SubsInfo`);
  }

  // Abonnement à un thème
  public subscribeToTheme(themeId: number): Observable<ThemeSubInfo[]> {
    return this.httpClient.post<ThemeSubInfo[]>(
      `${environment.baseUrl}${this.pathService}/subscribe`,
      {
        themeId,
      }
    );
  }

  // Désabonnement d'un thème
  public unsubscribeFromTheme(themeId: number): Observable<Theme[]> {
    return this.httpClient.request<Theme[]>(
      'delete',
      `${environment.baseUrl}${this.pathService}/unsubscribe`,
      { body: { themeId } }
    );
  }
}
