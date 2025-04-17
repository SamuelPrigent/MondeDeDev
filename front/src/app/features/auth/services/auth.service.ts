import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
// login
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformation } from '../../../../app/interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'auth';

  constructor(private httpClient: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(
      `${environment.baseUrl}${this.pathService}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(
      `${environment.baseUrl}${this.pathService}/login`,
      loginRequest
    );
  }
}
