import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';
// import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
// import { SessionService } from 'src/app/services/session.service';

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
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router //   private sessionService: SessionService
  ) {}

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.subscription.add(
      this.authService.login(loginRequest).subscribe({
        next: () =>
          //   response: SessionInformation
          {
            // this.sessionService.logIn(response); // for guards unauth, auth
            // TODO persist token ? // for interceptor (qui embarque le token pour les request)
            this.router.navigate(['/home']);
          },
        //   error: error => this.onError = true,
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
