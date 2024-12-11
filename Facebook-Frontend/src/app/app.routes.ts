import { Routes } from '@angular/router';
import { VideosComponent } from './features/videos/page/videos.component';
import { NotFoundComponent } from './features/not-found/page/not-found.component';
import { HomeComponent } from './features/home/page/home.component';
import { LoginComponent } from './features/auth/page/login.component';
import { ProfileComponent, resolveUserName } from './features/profile/page/profile.component';
import { profileRoutes } from './features/profile/profile.routes';


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
    path: ':pathname',
    component: ProfileComponent,
    children: profileRoutes,
    resolve: {
      fullname: resolveUserName
    }
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];