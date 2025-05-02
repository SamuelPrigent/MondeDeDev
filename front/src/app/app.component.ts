import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { SessionService } from './services/session.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'front';
  openMenu = false;
  private subscription: Subscription = new Subscription();

  constructor(private router: Router, private sessionService: SessionService) {}

  ngOnInit(): void {
    // Écouter les événements de navigation
    this.subscription.add(
      this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
        window.scrollTo(0, 0); // Faire défiler vers le haut
      })
    );
  }

  ngOnDestroy(): void {
    // Nettoyer l'abonnement
    this.subscription.unsubscribe();
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }
}
