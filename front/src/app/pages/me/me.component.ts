import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../interfaces/user.interface';
import { AuthService } from 'src/app/features/auth/services/auth.service'; // get Id & user info
import { UserService } from '../../services/user.service'; // putById
import { PutUserRequest } from 'src/app/interfaces/putUserRequest.interface';
import { SessionService } from 'src/app/services/session.service';
import { Title } from '@angular/platform-browser';
import { ThemeService } from 'src/app/services/theme.service';
import { Observable } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme.interface';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  public userForm: FormGroup;
  public user: User | undefined;
  public themes$!: Observable<Theme[]>;
  public hide = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private sessionService: SessionService,
    private titleService: Title,
    private themeService: ThemeService,
    private snackBar: MatSnackBar
  ) {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.min(3), Validators.max(20)]],
      email: ['', [Validators.required, Validators.min(3), Validators.email, Validators.max(50)]],
      password: ['', [Validators.required, Validators.min(3), Validators.max(20)]],
    });
  }

  ngOnInit(): void {
    this.titleService.setTitle('Profil utilisateur / MDD');
    // form values for edit account
    this.authService.me().subscribe((user: User) => {
      this.user = user;
      this.userForm.patchValue({
        username: user.username,
        email: user.email,
        password: '',
      });
    });
    // user themes
    const userId = this.sessionService.sessionInformation?.userId;
    if (userId) {
      this.themes$ = this.themeService.getThemesByUserId(userId);
    }
  }

  public putUser(): void {
    if (this.user) {
      const formValue = this.userForm.value;
      const putUserRequest: PutUserRequest = {
        username: formValue.username,
        email: formValue.email,
        password: formValue.password,
      };
      this.userService.putById(this.user.id, putUserRequest).subscribe(
        token => {
          this.sessionService.logIn(token);
          // snackbar de confirmation de la requête
          this.snackBar.open('Profil modifié avec succès', 'Fermer', { duration: 2000 });
        },
        error => {
          console.error("Erreur lors de la mise à jour de l'utilisateur:", error);
        }
      );
    } else {
      console.log('Utilisateur non défini');
    }
  }

  public back(): void {
    window.history.back();
  }

  public unsubscribe(themeId: number) {
    // à implémenter dans theme.service
    console.log('unsubscribe =>', themeId);
    // refresh themes while unsubscribe to the theme
    this.themes$ = this.themeService.unsubscribeFromTheme(themeId);
  }
}
