// angular
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
// angular material
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
// for put user
import { ReactiveFormsModule } from '@angular/forms'; // Importez ReactiveFormsModule
import { MatInputModule } from '@angular/material/input'; // Importez MatInputModule
import { MatFormFieldModule } from '@angular/material/form-field'; // Importez MatFormFieldModule

// utils
import { JwtInterceptor } from './interceptors/jwt.interceptor';
// component
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MeComponent } from './components/me/me.component';

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatInputModule,
  MatFormFieldModule,
];

@NgModule({
  declarations: [AppComponent, HomeComponent, MeComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatButtonModule,
    ReactiveFormsModule, // for put user
    ...materialModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],

  bootstrap: [AppComponent],
})
export class AppModule {}
