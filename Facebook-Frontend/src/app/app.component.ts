import { Component, inject, Injectable } from '@angular/core';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';
import { environment } from './environments/environment';
import { TokenService } from './core/services/token.service';
import { ViewportScroller } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class ScrollService {
  private scrollPositions = new Map<string, [number, number]>();

  constructor(private viewportScroller: ViewportScroller) {}

  // Lưu vị trí cuộn cho route con (sử dụng ActivatedRoute để nhận tên path con)
  saveScrollPosition(routePath: string): void {
    const position = this.viewportScroller.getScrollPosition();
    this.scrollPositions.set(routePath, position);
  }

  // Khôi phục vị trí cuộn cho route con
  restoreScrollPosition(routePath: string): void {
    const position = this.scrollPositions.get(routePath);
    if (position) {
      requestAnimationFrame(() => this.viewportScroller.scrollToPosition(position));
    }
  }

  // Xóa vị trí cuộn khi không cần thiết nữa
  clearScrollPosition(routePath: string): void {
    this.scrollPositions.delete(routePath);
  }
}

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
  private currentUrl: string = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private scrollService: ScrollService
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Lưu vị trí cuộn khi chuyển đến route con mới
        const currentRoute = this.getCurrentRoutePath();
        this.scrollService.saveScrollPosition(currentRoute);

        // Khôi phục vị trí cuộn khi chuyển đến route con đã lưu
        this.scrollService.restoreScrollPosition(currentRoute);
      }
    });
  }

  // Lấy path route con từ ActivatedRoute
  private getCurrentRoutePath(): string {
    const path = this.route.snapshot.firstChild?.url?.join('/') || '';
    return path;
  }

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
