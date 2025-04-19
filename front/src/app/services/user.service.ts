import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PutUserRequest } from '../interfaces/putUserRequest.interface';
import { environment } from 'src/environments/environment';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'user';

  constructor(private httpClient: HttpClient) {}

  // return un nouveau token de connexion
  public putById(id: number, putUserRequest: PutUserRequest): Observable<SessionInformation> {
    return this.httpClient.put<SessionInformation>(
      `${environment.baseUrl}${this.pathService}/${id}`,
      putUserRequest
    );
  }
}
