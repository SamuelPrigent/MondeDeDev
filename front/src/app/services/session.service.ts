import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private readonly STORAGE_KEY = 'session_info';

  constructor() {
    // charger la clé dans le state (après un refresh)
    this.loadSessionFromStorage();
  }

  // ======= methods =======

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(sessionInfo: SessionInformation): void {
    this.sessionInformation = sessionInfo;
    this.isLogged = true;
    // Save token in localStorage
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(sessionInfo));
    this.next();
  }

  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    // Remove token from localStorage
    localStorage.removeItem(this.STORAGE_KEY);
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  private loadSessionFromStorage(): void {
    const storedSession = localStorage.getItem(this.STORAGE_KEY);
    if (storedSession) {
      this.sessionInformation = JSON.parse(storedSession);
      this.isLogged = true;
      this.next();
    }
  }

  // à implémenter potentiellement si une erreur 403 token expiré sur une requêtes
  // on appelle sessionSerivce pour Logout()
}
