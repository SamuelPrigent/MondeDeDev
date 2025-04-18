import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PutUserRequest } from '../interfaces/putUserRequest.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'user';

  constructor(private httpClient: HttpClient) {}

  public putById(id: number, putUserRequest: PutUserRequest): Observable<PutUserRequest> {
    return this.httpClient.put<PutUserRequest>(
      `${environment.baseUrl}${this.pathService}/${id}`,
      putUserRequest
    );
  }
}
