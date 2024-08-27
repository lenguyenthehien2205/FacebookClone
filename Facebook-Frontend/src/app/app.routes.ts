import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/shared/header/header.component';

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
    path: 'header',
    component: HeaderComponent
  }
];
