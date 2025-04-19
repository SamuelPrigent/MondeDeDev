import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from 'src/app/pages/home/home.component';

const routes: Routes = [
  { title: 'Home', path: '', component: HomeComponent },
  { title: 'Register', path: 'register', component: RegisterComponent },
  { title: 'Login', path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
