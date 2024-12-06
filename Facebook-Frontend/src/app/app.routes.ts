import { Routes } from '@angular/router';
import { VideosComponent } from './features/videos/page/videos.component';
import { NotFoundComponent } from './features/not-found/page/not-found.component';
import { HomeComponent } from './features/home/page/home.component';
import { LoginComponent } from './features/auth/page/login.component';
import { ProfileComponent } from './features/profile/page/profile.component';

export const routes: Routes = [
  {
    path: '',
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
    path: ':pathname',
    component: ProfileComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
