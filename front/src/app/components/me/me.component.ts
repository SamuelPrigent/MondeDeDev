import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/features/auth/services/auth.service';
import { User } from '../../interfaces/user.interface';
// import { SessionService } from '../../services/session.service';
// import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  public user: User | undefined;

  constructor(private authService: AuthService) // private sessionService: SessionService
  {}
  public ngOnInit(): void {
    // get user
    this.authService.me().subscribe((user: User) => (this.user = user));
    // console.log(this.sessionService.sessionInformation);
  }

  //   public putUser(): void {
  //   }

  public back(): void {
    window.history.back();
  }
}
