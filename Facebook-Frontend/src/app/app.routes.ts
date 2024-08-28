import { Routes } from '@angular/router';
import { VideosComponent } from './components/videos/videos.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { HomeComponent } from './components/home/home.component';
import { HistoryComponent } from './components/shared/header/history/history.component';

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
    path: 'videos',
    component: VideosComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
