import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
// import { SessionInformation } from './interfaces/sessionInformation.interface';
// import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  // title = 'front';

  constructor(
    private authService: AuthService,
    private router: Router // private sessionService: SessionService
  ) {}

  public $isLogged(): Observable<boolean> {
    // return this.sessionService.$isLogged();
    return new Observable<boolean>((observer) => observer.next(false)); // logged false en dur
  }

  public logout(): void {
    // this.sessionService.logOut();
    this.router.navigate(['login']);
  }
}
