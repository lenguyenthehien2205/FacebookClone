import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ProfileService } from '../core/services/profile.service';

export const pathnameExistsGuard: CanActivateFn = (route, state) => {
  const profileService = inject(ProfileService);
  const router = inject(Router);
  const pathname = route.paramMap.get('pathname');

  if (!pathname) {
    router.navigate(['/not-found']);
    return of(false);
  }

  return profileService.checkPathname(pathname).pipe(
    map(exists => {
      if (!exists) {
        router.navigate(['/not-found']);
        return false;
      }
      return true;
    }),
    catchError(() => {
      router.navigate(['/not-found']);
      return of(false);
    })
  );
}; 