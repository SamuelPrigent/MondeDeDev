import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
// components
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';

const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatToolbarModule,
];

@NgModule({
  declarations: [RegisterComponent, LoginComponent],
  imports: [AuthRoutingModule, CommonModule, FormsModule, ReactiveFormsModule, ...materialModules],
})
export class AuthModule {}
