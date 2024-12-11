import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-profile-about',
  templateUrl: './profile-about.component.html',
  styleUrl: './profile-about.component.css'
})
export class ProfileAboutComponent implements OnInit{
  pathname: string | null = '';
  activeItemNavItem: string | null = null;
  route = inject(ActivatedRoute);
  router = inject(Router);
  routerSubscription!: Subscription;
  cdr = inject(ChangeDetectorRef);
  navItems = [
    { name: 'Tổng quan', url: 'overview' },
    { name: 'Công việc và học vấn', url: 'work' },
    { name: 'Nơi từng sống', url: 'place' },
    { name: 'Thông tin liên hệ và cơ bản', url: 'info' }
  ];
  onSelectNavItem(name: string) {
    this.activeItemNavItem = name;
  }
  ngOnInit(): void {
    this.pathname = this.route.snapshot.paramMap.get('pathname');
    const pathAfterHostname = this.router.url;
    this.updateActiveNavItem(pathAfterHostname);
    // Lắng nghe sự kiện điều hướng hoàn tất
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd || event instanceof NavigationStart) {
        const updatedPathAfterHostname = this.router.url;
        this.updateActiveNavItem(updatedPathAfterHostname);
      }
    });
  }
  getRouterLink(url: string): string {
    const pathnameValue = this.pathname;
    if (pathnameValue) {
      return `/${pathnameValue}/about/${url}`;
    }
    return `/${url}`;
  }
  private updateActiveNavItem(url: string): void {
    const baseUrl = `/${this.pathname}/about`;
    console.log(baseUrl);
    if (url.startsWith(`${baseUrl}/overview`)) {
      this.activeItemNavItem = 'Tổng quan';
    } else if (url.startsWith(`${baseUrl}/work`)) {
      this.activeItemNavItem = 'Công việc và học vấn';
    } else if (url.startsWith(`${baseUrl}/place`)) {
      this.activeItemNavItem = 'Nơi từng sống';
    } else if (url.startsWith(`${baseUrl}/info`)) {
      this.activeItemNavItem = 'Thông tin liên hệ và cơ bản';
    } else {
      this.activeItemNavItem = 'Tổng quan';  // Mặc định
    }
    this.cdr.detectChanges();  // Đảm bảo giao diện được cập nhật
  }
  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
}
