import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
// components
import { NotFoundComponent } from './components/not-found/not-found.component';
import { MeComponent } from './components/me/me.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ArticleDetailComponent } from './pages/articleDetail/articleDetail.component';
import { CreateArticleComponent } from './pages/createArticle/createArticle.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule),
  },
  //   { path: 'home', canActivate: [UnauthGuard], component: HomeComponent },
  //   auth
  {
    path: 'articles',
    canActivate: [AuthGuard],
    component: ArticlesComponent,
  },
  {
    path: 'articles/:id',
    canActivate: [AuthGuard],
    component: ArticleDetailComponent,
  },
  {
    path: 'createArticle',
    canActivate: [AuthGuard],
    component: CreateArticleComponent,
  },

  {
    path: 'themes',
    canActivate: [AuthGuard],
    component: ThemesComponent,
  },
  { path: 'me', canActivate: [AuthGuard], component: MeComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
