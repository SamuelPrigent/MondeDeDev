import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
// components
import { NotFoundComponent } from './components/not-found/not-found.component';
import { MeComponent } from './components/me/me.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemesComponent } from './pages/themes/themes.component';
// import { HomeComponent } from './pages/home/home.component';

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
