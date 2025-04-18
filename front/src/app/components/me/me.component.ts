import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { User } from '../../interfaces/user.interface';
import { AuthService } from 'src/app/features/auth/services/auth.service'; // get Id & user info
import { UserService } from '../../services/user.service'; // putById
import { PutUserRequest } from 'src/app/interfaces/putUserRequest.interface';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  public userForm: FormGroup;
  public user: User | undefined;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private userService: UserService
  ) {
    this.userForm = this.fb.group({
      username: [''],
      email: [''],
      password: [''],
    });
  }

  ngOnInit(): void {
    this.authService.me().subscribe((user: User) => {
      this.user = user;
      this.userForm.patchValue({
        username: user.username,
        email: user.email,
        password: '',
      });
    });
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
        updatedUser => {
          console.log('Utilisateur mis à jour:', updatedUser);
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
}
