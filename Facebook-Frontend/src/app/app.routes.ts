import { Routes } from '@angular/router';
import { VideosComponent } from './features/components/videos/videos.component';
import { NotFoundComponent } from './features/components/not-found/not-found.component';
import { HomeComponent } from './features/components/home/home.component';
import { LoginComponent } from './features/components/auth/login/login.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
    // loadComponent: () =>
    //   import('./components/shared/header/history/history.component').then(
    //     (mod) => mod.HistoryComponent
    //   ),
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'videos',
    component: VideosComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
