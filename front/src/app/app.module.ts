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
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

// for put user
import { ReactiveFormsModule } from '@angular/forms'; // for Put User (in /me)
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

// utils
import { JwtInterceptor } from './interceptors/jwt.interceptor';
// pipe
import { DateFormatPipe } from './pipe/date-format.pipe';
// component
import { HomeComponent } from './pages/home/home.component';
import { AppComponent } from './app.component';
import { MeComponent } from './pages/me/me.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ArticleDetailComponent } from './pages/articleDetail/articleDetail.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { CreateArticleComponent } from './pages/createArticle/createArticle.component';

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatInputModule,
  MatFormFieldModule,
  MatSelectModule,
  MatOptionModule,
  MatProgressSpinnerModule,
];

@NgModule({
  declarations: [
    AppComponent,
    MeComponent,
    ArticlesComponent,
    ThemesComponent,
    HomeComponent,
    ArticleDetailComponent,
    NotFoundComponent,
    CreateArticleComponent,
    DateFormatPipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatButtonModule,
    ReactiveFormsModule,
    ...materialModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],

  bootstrap: [AppComponent],
})
export class AppModule {}
