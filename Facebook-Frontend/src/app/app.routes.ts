import { Routes } from '@angular/router';
import { VideosComponent } from './features/videos/videos.component';
import { NotFoundComponent } from './features/not-found/not-found.component';
import { HomeComponent } from './features/home/home.component';
import { HistoryComponent } from './core/header/components/history/history.component';

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
