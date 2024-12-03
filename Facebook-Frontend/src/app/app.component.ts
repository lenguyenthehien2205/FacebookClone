import { Component, inject } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';
import { environment } from './environments/environment';
import { TokenService } from './core/services/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  tokenService = inject(TokenService);
  title = 'facebook-clone';
  isAuthenticated = true;
  private routerSubscription!: Subscription;
  constructor(private router: Router) {}

  ngOnInit(): void {
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const pathAfterHostname = this.router.url;
        if (pathAfterHostname.includes('/login')) {
          this.isAuthenticated = false;
        } else {
          this.isAuthenticated = true;
        }
      }
    });
  }

  ngOnDestroy(): void {
    // Hủy đăng ký để tránh rò rỉ bộ nhớ
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
}
