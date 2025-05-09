import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnDestroy {
  public hide = true;
  public onError = false;
  private subscription = new Subscription();

  public form = this.fb.group({
    userId: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.min(3)]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService,
    private titleService: Title
  ) {}

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.subscription.add(
      this.authService.login(loginRequest).subscribe({
        next: (response: SessionInformation) => {
          // update sessionService avec la réponse de authService for state utilisés par les (guards : unauth, auth)
          this.sessionService.logIn(response);
          this.router.navigate(['/articles']);
        },
        error: () => (this.onError = true),
      })
    );
  }

  ngOnInit(): void {
    this.titleService.setTitle('Connexion');
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public back(): void {
    window.history.back();
  }
}
