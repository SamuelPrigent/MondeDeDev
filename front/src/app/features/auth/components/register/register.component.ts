import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnDestroy {
  public onError = false;
  private subscription = new Subscription();

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    username: ['', [Validators.required, Validators.min(3), Validators.max(20)]],
    password: ['', [Validators.required, Validators.min(3), Validators.max(40)]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private titleService: Title
  ) {}

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.subscription.add(
      this.authService.register(registerRequest).subscribe({
        next: (_: void) => this.router.navigate(['/login']),
        error: _ => (this.onError = true),
      })
    );
  }

  ngOnInit(): void {
    this.titleService.setTitle('Inscription');
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public back(): void {
    window.history.back();
  }
}
